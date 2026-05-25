import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class GenerateSounds {

    private static final int SAMPLE_RATE = 44100;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        File soundsDir = new File("assets/sounds");

        if (!soundsDir.exists()) {
            soundsDir.mkdirs();
        }

        writeTone("assets/sounds/attack.wav", 0.18, 880, 0.45);
        writeNoiseTone("assets/sounds/enemy_kill.wav", 0.35, 160, 0.45);
        writeRisingTone("assets/sounds/wave.wav", 0.55, 350, 1000, 0.42);
        writeTwoTone("assets/sounds/upgrade.wav", 0.32, 650, 1100, 0.38);
        writeFallingTone("assets/sounds/game_over.wav", 0.80, 300, 130, 0.45);

        System.out.println("Sound files generated successfully.");
    }

    private static void writeTone(String path, double duration, double frequency, double volume) throws IOException {
        writeWav(path, duration, (t, i, total) -> {
            double envelope = 1.0 - (double) i / total;
            return Math.sin(2.0 * Math.PI * frequency * t) * volume * envelope;
        });
    }

    private static void writeNoiseTone(String path, double duration, double frequency, double volume) throws IOException {
        writeWav(path, duration, (t, i, total) -> {
            double envelope = 1.0 - (double) i / total;
            double tone = Math.sin(2.0 * Math.PI * frequency * t);
            double noise = RANDOM.nextDouble() * 2.0 - 1.0;
            return (tone * 0.7 + noise * 0.3) * volume * envelope;
        });
    }

    private static void writeRisingTone(String path, double duration, double startFreq, double endFreq, double volume) throws IOException {
        writeWav(path, duration, (t, i, total) -> {
            double progress = (double) i / total;
            double frequency = startFreq + (endFreq - startFreq) * progress;
            double envelope = 1.0 - Math.max(0.0, progress - 0.8) / 0.2;
            return Math.sin(2.0 * Math.PI * frequency * t) * volume * envelope;
        });
    }

    private static void writeFallingTone(String path, double duration, double startFreq, double endFreq, double volume) throws IOException {
        writeWav(path, duration, (t, i, total) -> {
            double progress = (double) i / total;
            double frequency = startFreq + (endFreq - startFreq) * progress;
            double envelope = 1.0 - progress;
            return Math.sin(2.0 * Math.PI * frequency * t) * volume * envelope;
        });
    }

    private static void writeTwoTone(String path, double duration, double frequencyA, double frequencyB, double volume) throws IOException {
        writeWav(path, duration, (t, i, total) -> {
            double frequency = i < total / 2 ? frequencyA : frequencyB;
            double envelope = 1.0 - (double) i / total;
            return Math.sin(2.0 * Math.PI * frequency * t) * volume * envelope;
        });
    }

    private static void writeWav(String path, double duration, SampleGenerator generator) throws IOException {
        int totalSamples = (int) (SAMPLE_RATE * duration);
        int dataSize = totalSamples * 2;
        int fileSize = 36 + dataSize;

        try (FileOutputStream out = new FileOutputStream(path)) {
            writeString(out, "RIFF");
            writeIntLE(out, fileSize);
            writeString(out, "WAVE");

            writeString(out, "fmt ");
            writeIntLE(out, 16);
            writeShortLE(out, 1);
            writeShortLE(out, 1);
            writeIntLE(out, SAMPLE_RATE);
            writeIntLE(out, SAMPLE_RATE * 2);
            writeShortLE(out, 2);
            writeShortLE(out, 16);

            writeString(out, "data");
            writeIntLE(out, dataSize);

            for (int i = 0; i < totalSamples; i++) {
                double t = (double) i / SAMPLE_RATE;
                double value = generator.generate(t, i, totalSamples);

                if (value > 1.0) {
                    value = 1.0;
                }

                if (value < -1.0) {
                    value = -1.0;
                }

                short sample = (short) (value * 32767);
                writeShortLE(out, sample);
            }
        }
    }

    private static void writeString(FileOutputStream out, String value) throws IOException {
        out.write(value.getBytes());
    }

    private static void writeIntLE(FileOutputStream out, int value) throws IOException {
        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
        out.write((value >> 16) & 0xff);
        out.write((value >> 24) & 0xff);
    }

    private static void writeShortLE(FileOutputStream out, int value) throws IOException {
        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
    }

    private interface SampleGenerator {
        double generate(double t, int index, int totalSamples);
    }
}
