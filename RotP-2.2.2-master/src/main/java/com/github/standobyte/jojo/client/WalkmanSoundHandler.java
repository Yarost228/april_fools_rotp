package com.github.standobyte.jojo.client;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.capability.item.cassette.CassetteCap;
import com.github.standobyte.jojo.capability.item.cassette.CassetteCap.TrackList;
import com.github.standobyte.jojo.capability.item.cassette.CassetteCapProvider;
import com.github.standobyte.jojo.client.sound.WalkmanRewindSound;
import com.github.standobyte.jojo.client.sound.WalkmanTrackSound;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.item.WalkmanItem;
import com.github.standobyte.jojo.item.WalkmanItem.PlaybackMode;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromclient.ClWalkmanControlsPacket;
import com.github.standobyte.jojo.util.reflection.ClientReflection;
import com.github.standobyte.jojo.util.utils.GeneralUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = JojoMod.MOD_ID, value = Dist.CLIENT)
public class WalkmanSoundHandler {
    private static Playlist playlist = null;
    
    @Nullable
    public static Playlist getPlaylist(int walkmanId) {
        if (playlist != null && playlist.walkmanId == walkmanId) {
            return playlist;
        }
        return null;
    }
    
    @Nullable
    public static Playlist getCurrentPlaylist() {
        return playlist;
    }
    
    public static Playlist initPlaylist(Map<CassetteSide, List<Track>> cassetteTracks, ItemStack cassetteItem, int walkmanId) {
        playlist = null;
        cassetteItem.getCapability(CassetteCapProvider.CAPABILITY).ifPresent(cap -> {
            if (!cap.getTracks().isBroken()) {
                playlist = new Playlist(cassetteTracks, cap, walkmanId);
            }
        });
        return playlist;
    }
    
    public static void clearPlaylist() {
        if (playlist != null) {
            playlist.stopPlaying();
            playlist = null;
        }
    }
    
    
    
    public static class Playlist {
        private final Map<CassetteSide, List<Track>> cassetteTracks;
        private final int distortion;
        private final int walkmanId;

        private float volume;
        private PlaybackMode playbackMode;
        
        private TrackInfo currentTrack;
        private TrackInfo fastForwardTrack;
        private boolean currentTrackIsLast;
        private TrackInfo rewindTrack;
        private TrackInfo flipSideTrack;
        
        private boolean isPlaying;
        private WalkmanTrackSound currentSound = null;
        
        private int rewindSoundTicks;
        private boolean playCurrentSoundAfterRewind;
        private WalkmanRewindSound rewindSound = null;
        private IndicatorStatus indicatorStatus;
        
        private Playlist(Map<CassetteSide, List<Track>> cassetteTracks, CassetteCap cassette, int walkmanId) {
            this.cassetteTracks = cassetteTracks;
            this.distortion = cassette.getGeneration();
            this.walkmanId = walkmanId;
        }
        
        public void setTrack(TrackInfo track) {
            currentTrack = track;
            fastForwardTrack = rewindTrack = flipSideTrack = null;
            
            CassetteSide currentSide = track.side;
            
            List<Track> tracksThisSide = cassetteTracks.get(currentSide);
            List<Track> tracksOppositeSide = cassetteTracks.get(currentSide.getOpposite());
            
            int trackNumber = MathHelper.clamp(track.number, 0, tracksThisSide.size() - 1);

            if (trackNumber < tracksThisSide.size() - 1) {
                fastForwardTrack = TrackInfo.of(cassetteTracks, currentSide, trackNumber + 1);
            }
            else if (!tracksOppositeSide.isEmpty()) {
                fastForwardTrack = TrackInfo.of(cassetteTracks, currentSide.getOpposite(), 0);
            }
            else if (!tracksThisSide.isEmpty()) {
                fastForwardTrack = TrackInfo.of(cassetteTracks, currentSide, 0);
            }
            currentTrackIsLast = isTrackLast(track);

            if (trackNumber > 0 && !tracksThisSide.isEmpty()) {
                rewindTrack = TrackInfo.of(cassetteTracks, currentSide, trackNumber - 1);
            }
            else {
                rewindTrack = currentTrack;
            }

            if (!tracksOppositeSide.isEmpty()) {
                int flipSideTrackNumber = Math.max(tracksThisSide.size(), tracksOppositeSide.size()) - 1 - trackNumber;
                flipSideTrack = TrackInfo.of(cassetteTracks, currentSide.getOpposite(), MathHelper.clamp(flipSideTrackNumber, 0, tracksOppositeSide.size() - 1));
            }
            
            if (currentTrack != null) {
                PacketManager.sendToServer(ClWalkmanControlsPacket.cassettePosition(walkmanId, currentTrack.side, currentTrack.number));
            }
        }
        
        public void playCurrentTrack() {
            playTrack(currentTrack);
        }
        
        private void playTrack(TrackInfo track) {
            if (currentSound != null) {
                currentSound.stop();
                currentSound = null;
            }
            if (track != null) {
                if (rewindSoundTicks > 0) {
                    playCurrentSoundAfterRewind = true;
                }
                else {
                    WalkmanTrackSound newSound = new WalkmanTrackSound(track.track.getSound(), SoundCategory.RECORDS, null, distortion);
                    currentSound = newSound;
                    newSound.setVolume(volume);
                    Minecraft mc = Minecraft.getInstance();
                    mc.getSoundManager().play(newSound);
                    mc.gui.setNowPlaying(track.track.getName());
                    playCurrentSoundAfterRewind = false;
                }
                isPlaying = true;
            }
            else {
                isPlaying = false;
                playCurrentSoundAfterRewind = false;
                setRewindSoundTicks(0, null);
            }
        }
        
        public void stopPlaying() {
            playTrack(null);
        }
        
        public void setAndPlayNext() {
            boolean stop = stopAfterCurrentTrack();
            setTrack(getFastForwardTrack());
            if (stop) {
                stopPlaying();
            }
            else {
                playCurrentTrack();
            }
        }
        
        public boolean stopAfterCurrentTrack() {
            return currentTrackIsLast && playbackMode == PlaybackMode.STOP_AT_THE_END;
        }
        
//        public void pause() {
//        }
        
        public void setRewindSoundTicks(int ticks, IndicatorStatus indicatorStatus) {
            this.rewindSoundTicks = ticks;
            this.indicatorStatus = indicatorStatus;
            if (ticks > 0 && (rewindSound == null || rewindSound.isStopped())) {
                rewindSound = new WalkmanRewindSound();
                Minecraft.getInstance().getSoundManager().play(rewindSound);
            }
        }
        
        public int getRewindSoundTicks() {
            return rewindSoundTicks;
        }
        
        public IndicatorStatus getIndicatorStatus() {
            return indicatorStatus;
        }
        
        private void tick() {
            if (rewindSoundTicks > 0) {
                rewindSoundTicks--;
                if (rewindSoundTicks == 0 && playCurrentSoundAfterRewind) {
                    playCurrentTrack();
                    indicatorStatus = null;
                }
            }
            
            else if (currentSound != null) {
                Minecraft mc = Minecraft.getInstance();
                
                if (mc.player != null) {
                    Predicate<ItemStack> walkmanPlaying = item -> {
//                        if (!item.isEmpty() && item.getItem() == ModItems.WALKMAN.get()) {
//                            return WalkmanItem.getId(item) == walkmanId;
//                        }
                        return false;
                    };
                    
                    boolean walkmanRemoved = GeneralUtil.findInInventory(mc.player.inventory, walkmanPlaying).isEmpty()
                            && !walkmanPlaying.test(mc.player.inventory.getCarried());
                    
                    if (walkmanRemoved) {
                        clearPlaylist();
                        return;
                    }
                }
                
                if (!mc.getSoundManager().isActive(currentSound)) {
                    if (mc.player != null && mc.level != null) {
                        setAndPlayNext();
                    }
                    else {
                        clearPlaylist();
                        return;
                    }
                }
                
                if (mc.player.tickCount % 100 == 0) {
                    Minecraft.getInstance().getMusicManager().stopPlaying();
                }
            }
        }
        
        public void setVolume(float volume) {
            this.volume = volume;
            if (currentSound != null) {
                currentSound.setVolume(volume);
            }
        }
        
        public void setPlaybackMode(PlaybackMode mode) {
            this.playbackMode = mode;
        }
        
        private boolean isTrackLast(TrackInfo track) {
            return track.number >= cassetteTracks.get(track.side).size() - 1 && 
                    (track.side == CassetteSide.SIDE_B || cassetteTracks.get(CassetteSide.SIDE_B).isEmpty());
        }
        
        public boolean isPlaying() {
            return isPlaying;
        }
        
        public TrackInfo getFlipSideTrack() {
            return flipSideTrack;
        }
        
        public TrackInfo getRewindTrack() {
            return rewindTrack;
        }
        
        public TrackInfo getFastForwardTrack() {
            return fastForwardTrack;
        }
        
        public Map<CassetteSide, List<Track>> getAllTracks() {
            return cassetteTracks;
        }
        
        public TrackInfo getCurrentTrack() {
            return currentTrack;
        }
    }
    
    public static enum IndicatorStatus {
        REWIND,
        FAST_FORWARD
    }
    
    
    @SubscribeEvent
    public static void musicTick(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && playlist != null) {
            playlist.tick();
        }
    }
    
    
    public static class TrackInfo {
        public final Track track;
        public final CassetteSide side;
        public final int number;
        
        private TrackInfo(Track track, CassetteSide side, int number) {
            this.track = track;
            this.side = side;
            this.number = number;
        }
        
        public static TrackInfo of(Map<CassetteSide, List<Track>> allTracks, CassetteSide side, int number) {
            List<Track> tracksThisSide = allTracks.get(side);
            if (number < 0 || number >= tracksThisSide.size()) {
                throw new IllegalArgumentException("The track number is supposed to be checked already");
            }
            return new TrackInfo(Objects.requireNonNull(tracksThisSide.get(number)), side, number);
        }
    }

    

    // FIXME ! (cassette) test on dedicated server
    public static Stream<Track> getTracksOnClient(TrackList trackSourcesList) {
        if (trackSourcesList.isBroken()) {
            return Stream.empty();
        }
        return trackSourcesList.getTracks().flatMap(trackSource -> {
            SoundEvent soundEvent = trackSource.getSoundEvent();
            if (soundEvent == null) return Stream.empty();
            SoundHandler soundManager = Minecraft.getInstance().getSoundManager();
            SoundEventAccessor soundEventAccessor = soundManager.getSoundEvent(soundEvent.getLocation());
            if (soundEventAccessor == null) return Stream.empty();
            Stream<Sound> loadedSounds = unpackSounds(soundManager, soundEventAccessor);
            Stream<Track> tracks = loadedSounds
                    .map(sound -> new Track(sound, shortened -> trackSource.trackName(sound.getLocation(), shortened)));
            return tracks;
        });
    }
    
    public static Map<CassetteSide, List<Track>> splitIntoSides(Stream<Track> cassetteTracks) {
        List<Track> tracks = cassetteTracks.collect(Collectors.toCollection(LinkedList::new));
        Map<CassetteSide, List<Track>> sides = new EnumMap<>(CassetteSide.class);
        if (tracks.isEmpty()) {
            sides.put(CassetteSide.SIDE_A, Collections.emptyList());
            sides.put(CassetteSide.SIDE_B, Collections.emptyList());
        }
        else {
            int lastSideATrack = (tracks.size() - 1) / 2;
            sides.put(CassetteSide.SIDE_A, tracks.subList(0, lastSideATrack + 1));
            sides.put(CassetteSide.SIDE_B, tracks.subList(lastSideATrack + 1, tracks.size()));
        }
        return sides;
    }
    
    public static enum CassetteSide {
        SIDE_A { @Override public CassetteSide getOpposite() { return SIDE_B; }},
        SIDE_B { @Override public CassetteSide getOpposite() { return SIDE_A; }};
        
        public abstract CassetteSide getOpposite();
    }
    
    private static Stream<Sound> unpackSounds(SoundHandler soundManager, ISoundEventAccessor<Sound> accessor) {
        if (accessor == null) {
            return Stream.of(SoundHandler.EMPTY_SOUND);
        }
        if (accessor instanceof SoundEventAccessor) {
            List<ISoundEventAccessor<Sound>> list = ClientReflection.getSubAccessorsList((SoundEventAccessor) accessor);
            return list.stream().flatMap(sound -> unpackSounds(soundManager, sound));
        }
        if (accessor instanceof Sound) {
            return Stream.of(accessor.getSound());
        }
        // FIXME ! (cassette) this... is not okay
        if ("net.minecraft.client.audio.SoundHandler$Loader$1".equals(accessor.getClass().getName())) {
            for (Field field : accessor.getClass().getDeclaredFields()) {
                if (field.getType() == ResourceLocation.class) {
                    try {
                        field.setAccessible(true);
                        ResourceLocation id = (ResourceLocation) field.get(accessor);
                        SoundEventAccessor nextAccessor = soundManager.getSoundEvent(id);
                        if (nextAccessor != null) {
                            return unpackSounds(soundManager, nextAccessor);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // FIXME ! (cassette) Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return Stream.of(accessor.getSound());
    }
    
    public static class Track {
        private final Sound sound;
        private final Function<Boolean, IFormattableTextComponent> name;
        
        private Track(Sound sound, Function<Boolean, IFormattableTextComponent> name) {
            this.sound = sound;
            this.name = name;
        }
        
        public Sound getSound() {
            return sound;
        }
        
        public IFormattableTextComponent getName() {
            return getName(false);
        }
        
        public IFormattableTextComponent getName(boolean shortened) {
            return name.apply(shortened);
        }
    }
}
