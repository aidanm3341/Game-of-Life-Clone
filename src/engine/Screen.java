package engine;

public class Screen {

	public final int w, h;
	public int[] pixels;

	public int xOffset, yOffset;


	public Screen(int w, int h) {
		this.w = w;
		this.h = h;

		pixels = new int[w * h];
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void clear(int color) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = color;
	}

	public void render(int x, int y, int col) {
		x -= xOffset;
		y -= yOffset;
		if (y < 0 || y >= h) return;
		if (x < 0 || x >= w) return;
		pixels[x + y * w] = col;
	}

	public void renderRect(int x, int y, int width, int height, int col){
		for(int i=0; i<width; i++){
				for(int j=0; j<height; j++){
					if(i == 0 || i == width-1 || j == 0 || j == height-1)
						render(i+x, j+y, col);
				}
		}
	}

	public void renderFillRect(int x, int y, int width, int height, int col){
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				render(i+x, j+y, col);
			}
		}
	}
}