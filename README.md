
# HSV Color Segmentation

This project implements a simple **image segmentation** algorithm using the **HSV color space**, specifically the **Hue** channel. The program reads an input RGB image and highlights pixels within a user-defined **hue range**, displaying them in original color while turning all others to grayscale.

## 🛠️ Implementation Details

- **Programming Language:** Java (no external libraries)
- **Approach:** 
  - Reads a **512x512 RGB image** file with 8 bits per channel (24 bits per pixel).
  - Converts each pixel from **RGB to HSV** using the standard conversion formula.
  - Applies two **hue thresholds** (`h1` and `h2`) provided via command-line arguments.
  - Displays pixels:
    - Inside the hue range: retain original color.
    - Outside the hue range: convert to grayscale using luminance approximation.
- **Output:** Displays the segmented image in a window or outputs it to a file.

## 📖 Usage
```bash
# Compile the program
javac ImageDisplay.java

# Run the program with your input image and hue thresholds
java ImageDisplay path_to_image.rgb h1 h2
```

### Example:
```bash
java ImageDisplay roses_image1.rgb 60 120
```
- **roses_image1.rgb** – Path to the input image.
- **60 120** – Hue thresholds defining the color range to retain.

## 📚 Key Concepts
- **HSV Color Space:** Separates color information (hue) from brightness and saturation, making it ideal for color segmentation.
- **Hue Range Filtering:** Simple thresholding in the hue dimension provides an intuitive way to isolate colors.
- **Grayscale Conversion:** Non-matching pixels are converted to grayscale to highlight the segmentation effect.

## 💻 Future Improvements
- Dynamic output image generation (currently, the code might just display the image).
- Support for additional color spaces (e.g., Lab).
- Interactive threshold adjustment via UI.

