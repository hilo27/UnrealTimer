package UnrealTimer;
// Created by Руслан on 08.07.2019.

import javafx.scene.media.AudioClip;

public class Sound {

    /**
     * URI for audio resources
     */
    public interface Resources {
        interface Female {
            String TWENTY = Sound.class.getResource("/UnrealTimer/Sound/female_20.wav").toExternalForm();
            String TEN = Sound.class.getResource("/UnrealTimer/Sound/female_10.wav").toExternalForm();
            String FIVE = Sound.class.getResource("/UnrealTimer/Sound/female_5.wav").toExternalForm();
            String FOUR = Sound.class.getResource("/UnrealTimer/Sound/female_4.wav").toExternalForm();
            String THREE = Sound.class.getResource("/UnrealTimer/Sound/female_3.wav").toExternalForm();
            String TWO = Sound.class.getResource("/UnrealTimer/Sound/female_2.wav").toExternalForm();
            String ONE = Sound.class.getResource("/UnrealTimer/Sound/female_1.wav").toExternalForm();
        }

        interface Male {
            String TWENTY = Sound.class.getResource("/UnrealTimer/Sound/male_20.wav").toExternalForm();
            String TEN = Sound.class.getResource("/UnrealTimer/Sound/male_10.wav").toExternalForm();
            String FIVE = Sound.class.getResource("/UnrealTimer/Sound/male_5.wav").toExternalForm();
            String FOUR = Sound.class.getResource("/UnrealTimer/Sound/male_4.wav").toExternalForm();
            String THREE = Sound.class.getResource("/UnrealTimer/Sound/male_3.wav").toExternalForm();
            String TWO = Sound.class.getResource("/UnrealTimer/Sound/male_2.wav").toExternalForm();
            String ONE = Sound.class.getResource("/UnrealTimer/Sound/male_1.wav").toExternalForm();
        }
    }

    /**
     * Female voices
     */
    public static class Female {
        public static AudioClip TWENTY = new AudioClip(Resources.Female.TWENTY);
        public static AudioClip TEN = new AudioClip(Resources.Female.TEN);
        public static AudioClip FIVE = new AudioClip(Resources.Female.FIVE);
        public static AudioClip FOUR = new AudioClip(Resources.Female.FOUR);
        public static AudioClip THREE = new AudioClip(Resources.Female.THREE);
        public static AudioClip TWO = new AudioClip(Resources.Female.TWO);
        public static AudioClip ONE = new AudioClip(Resources.Female.ONE);
    }

    /**
     * Male voices
     */
    public static class Male {
        public static AudioClip TWENTY = new AudioClip(Resources.Male.TWENTY);
        public static AudioClip TEN = new AudioClip(Resources.Male.TEN);
        public static AudioClip FIVE = new AudioClip(Resources.Male.FIVE);
        public static AudioClip FOUR = new AudioClip(Resources.Male.FOUR);
        public static AudioClip THREE = new AudioClip(Resources.Male.THREE);
        public static AudioClip TWO = new AudioClip(Resources.Male.TWO);
        public static AudioClip ONE = new AudioClip(Resources.Male.ONE);
    }
}