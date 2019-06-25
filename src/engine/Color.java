package engine;

public class Color {

	public static int get(int a, int b, int c, int d) {
		return (get(d) << 24) + (get(c) << 16) + (get(b) << 8) + (get(a));
	}

	public static int getRGB(int r, int g, int b) {
		return (r << 16) + (g << 8) + b;
	}

	public static int getRed(int x){
		return (x & 0xff0000) >> 16;
	}

	public static int getGreen(int x){
		return (x & 0xff00) >> 8;
	}

	public static int getBlue(int x){
		return (x & 0xff);
	}

	public static int get(int d) {
		if (d < 0) return 255;
		int r = d / 100 % 10;
		int g = d / 10 % 10;
		int b = d % 10;
		return r * 36 + g * 6 + b;
	}
}