import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateSounds {

    private static final int SAMPLE_RATE = 44100;

    public static void main(String[] args) throws Exception {
        new File("assets/sounds").mkdirs();

        createSound("assets/sounds/attack.wav", 880, 0.18);
        createSound("assets/sounds/enemy_kill.wav", 180, 0.35);
        createSound("assets/sounds/wave.wav", 650, 0.55);
        createSound("assets/sounds/upgrade.wav", 1100, 0.30);
        createSound("assets/sounds/game_over.wav", 220, 0.80);

        System.out.println("Sound files generated successfully.");
    }

    private static void createSound(String path, double frequency, double duration) throws IOException {
        int samples = (int) (SAMPLE_RATE * duration);
        int dataSize = samples * 2;
        int fileSize = 36 + dataSize;

        try (FileOutputStream out = new FileOutputStream(path)) {
            writeString(out, "RIFF");
            writeInt(out, fileSize);
            writeString(out, "WAVE");

            writeString(out, "fmt ");
            writeInt(out, 16);
            writeShort(out, 1);
            writeShort(out, 1);
            writeInt(out, SAMPLE_RATE);
            writeInt(out, SAMPLE_RATE * 2);
            writeShort(out, 2);
            writeShort(out, 16);

            writeString(out, "data");
            writeInt(out, dataSize);

            for (int i = 0; i < samples; i++) {
                double t = (double) i / SAMPLE_RATE;
                double fade = 1.0 - (double) i / samples;
                double value = Math.sin(2.0 * Math.PI * frequency * t) * 0.5 * fade;
                short sample = (short) (value * 32767);
                writeShort(out, sample);
            }
        }
    }

    private static void writeString(FileOutputStream out, String value) throws IOException {
        out.write(value.getBytes());
    }

    private static void writeInt(FileOutputStream out, int value) throws IOException {
        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
        out.write((value >> 16) & 0xff);
        out.write((value >> 24) & 0xff);
    }

    private static void writeShort(FileOutputStream out, int value) throws IOException {
        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
    }
}
