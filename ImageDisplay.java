
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


public class ImageDisplay {

	JFrame frame;
	JLabel lbIm1;
	BufferedImage imgOne;

	// Modify the height and width values here to read and display an image with
  	// different dimensions. 
	int width = 512;
	int height = 512;

	/** Read Image RGB
	 *  Reads the image of given width and height at the given imgPath into the provided BufferedImage.
	 */

	private int[] byteToRGB(byte rgb[]){
		return new int[]{rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF};
	}

	private boolean convertToHSV (byte r, byte g, byte b, int h1, int h2){
		// https://en.wikipedia.org/wiki/HSL_and_HSV#From_RGB
		int rgb[] = byteToRGB(new byte[]{r, g, b});

		double red = rgb[0] * 1.0;
		double green = rgb[1] * 1.0;
		double blue = rgb[2] * 1.0;

		double xMax = Math.max(red, Math.max(green, blue));
		double xMin = Math.min(red, Math.min(green, blue));

		double v = xMax;
		double c = xMax - xMin;
		// double l = (xMax + xMin)/2.0;
		
		int h = 0; 
		if (c != 0){
		  if (v == red){
				h = (int) (60 * (((green - blue)/c) % 6));
			} else if (v == green){
				h = (int) (60 * (((blue - red)/c) + 2));
			} else if (v == blue){
				h = (int) (60 * (((red - green)/c) + 4));
			}
			if (h < 0) {
				h += 360;
			}
		}
		
		// System.out.println(h);
		if (h1 <= h && h <= h2){
			return true;
		} else {
			return false;
		}
	} 

	private int getGrayscalePixel(byte red, byte green, byte blue) {
		// Convert bytes to unsigned integers (0-255)
		int r = red & 0xFF;
		int g = green & 0xFF;
		int b = blue & 0xFF;
	
		// Calculate the grayscale value using the luminosity method
		// https://mmuratarat.github.io/2020-05-13/rgb_to_grayscale_formulas
		int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
		// int gray = (int)(r + g + b)/3;
	
		// Assign the grayscale value to the RGB channels to create the grayscale color
		return (0xFF << 24) | (gray << 16) | (gray << 8) | gray;
	}

	private void readImageRGB(int width, int height, String imgPath, int h1, int h2, BufferedImage img)
	{
		if (h1 < 0 || h2 < 0 || h1 >= 360 || h2 >= 360 || (h2 - h1 <= 0)){
			System.err.println("Invalid h1 || h2 values make sure h1 < h2 and 0 <= h1 and h2 < 360 ");
			System.exit(0);
		}
		try
		{
			int frameLength = width*height*3;

			File file = new File(imgPath);
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.seek(0);

			long len = frameLength;
			byte[] bytes = new byte[(int) len];

			raf.read(bytes);

			int ind = 0;
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 

					boolean inRange = convertToHSV(r, g, b, h1, h2);
					int pix;
					if (inRange){
						pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
						// img.setRGB(x,y,pix);
					} else {
						pix = getGrayscalePixel(r, g, b);
					}

					// int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x,y,pix);
					ind++;
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void showIms(String[] args){

		// Read in the specified image
		imgOne = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		readImageRGB(width, height, args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), imgOne);

		// Use label to display the image
		frame = new JFrame();
		GridBagLayout gLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gLayout);

		lbIm1 = new JLabel(new ImageIcon(imgOne));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		frame.getContentPane().add(lbIm1, c);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		ImageDisplay ren = new ImageDisplay();
		ren.showIms(args);
	}

}
