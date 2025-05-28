This project contains the starter code implemented in Java. This project is cross-platform and can be run on Windows,
Linux or Mac. You can use any editor or command line tools to work on this project.

This is a  program to read and display an image in JavaFX panels.

To run the code from command line, first compile with:

>> javac ImageDisplay.java

and then, you can run it with the path to the RGB file as a parameter

>> Usage: java ImageDisplay <path to rgb file> h1 h2
>> Example: java ImageDisplay ../roses_image_512x512.rgb 320 359  

Image Segmentation Details

In this project, the image pixels are segmented based on two hue threshold values (h1 and h2) in the HSV color space. The program assumes that:

 - The thresholds h1 and h2 will always satisfy h1 < h2.
 - Both h1 and h2 are integer values within the range [0, 360).
 - The hue of each pixel is calculated by converting its RGB values into the HSV color space.

If the pixel’s hue falls between h1 and h2, the pixel is kept in its original color. If not, the pixel is converted to grayscale using the luminosity method.

Color Space Conversion

For the conversion from RGB to HSV, we follow the standard formulas, which can be found here:

[Wikipedia - RGB to HSV Conversion](https://en.wikipedia.org/wiki/HSL_and_HSV#From_RGB)

For the conversion to grayscale using the luminosity method, we use the following formula:

Gray = 0.299 * R + 0.587 * G + 0.114 * B

[Grayscale Conversion](https://mmuratarat.github.io/2020-05-13/rgb_to_grayscale_formulas)