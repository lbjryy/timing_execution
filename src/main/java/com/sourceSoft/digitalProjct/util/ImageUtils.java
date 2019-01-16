package com.sourceSoft.digitalProjct.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class ImageUtils
{
  public static String PNG_TYPE = "png";

  public static String IMAGE_TYPE = ".png .jpg .jpeg .gif .bmp";

  private BufferedImage image = null;

  public void load(File imageFile)
    throws IOException
  {
    this.image = ImageIO.read(imageFile);
  }

  public BufferedImage cutTo(int x, int y, int tarWidth, int tarHeight, BufferedImage image1)
    throws FileNotFoundException
  {
    if (image1 == null) {
      throw new FileNotFoundException("image file not be load.please execute 'load' function agin.");
    }
    int iSrcWidth = getImageWidth();
    int iSrcHeight = getImageHeight();

    if ((iSrcWidth < tarWidth) || (iSrcHeight < tarHeight)) {
      throw new RuntimeException("Source image size too small.");
    }

    double dSrcScale = iSrcWidth * 1.0D / iSrcHeight;
    double dDstScale = tarWidth * 1.0D / tarHeight;
    int iDstHeight;
    int iDstWidth;
    if (dDstScale > dSrcScale) {
      iDstWidth = iSrcWidth;
      iDstHeight = (int)(iDstWidth * 1.0D / dDstScale);
    } else {
      iDstHeight = iSrcHeight;
      iDstWidth = (int)(iDstHeight * dDstScale);
    }

    return image1.getSubimage(x, y, tarWidth, tarHeight);
  }

  public void zoomDoesNotGenerate(int tarWidth, int tarHeight)
  {
    BufferedImage tagImage = new BufferedImage(tarWidth, tarHeight, 1);
    Image image = this.image.getScaledInstance(tarWidth, tarHeight, 4);
    Graphics g = tagImage.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    this.image = tagImage;
  }

  public void writeToImage(String fileName, String formatName)
    throws IOException
  {
    FileOutputStream out = null;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(this.image, formatName, bos);
      out = new FileOutputStream(fileName);
      out.write(bos.toByteArray());
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (out != null)
          out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static boolean zoomGenerateImage(String srcFile, String dstFile, int width, int height, String formatName)
  {
    try
    {
      ImageUtils zoom = new ImageUtils();
      zoom.load(new File(srcFile));
      zoom.zoomDoesNotGenerate(width, height);
      zoom.writeToImage(dstFile, formatName);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static byte[] getByteImatZoom(int width, int height, String formatName, InputStream inStream)
    throws IOException
  {
    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] bytes = null;
    try {
      ImageUtils imageUtils = new ImageUtils();
      byte[] buff = new byte[1024];
      int rc = -1;
      while ((rc = inStream.read(buff)) != -1) {
        swapStream.write(buff, 0, rc);
      }
      bytes = swapStream.toByteArray();
      imageUtils.image = byteToBufferedImage(bytes);
      int type = 1;
      if (PNG_TYPE.equals(formatName.toLowerCase())) {
        type = 2;
      }
      BufferedImage tagImage = new BufferedImage(width, height, type);
      Image image = imageUtils.image.getScaledInstance(width, height, 4);
      Graphics g = tagImage.getGraphics();
      g.drawImage(image, 0, 0, null);
      g.dispose();
      imageUtils.image = tagImage;

      ImageIO.write(imageUtils.image, formatName, bos);
      imageUtils.image.flush();
      bytes = bos.toByteArray();
      swapStream.flush();
      bos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (swapStream != null) {
        swapStream.close();
        swapStream = null;
      }
      if (bos != null) {
        bos.close();
        bos = null;
      }
    }
    return bytes;
  }

  public static InputStream byteToInputStream(byte[] bytes)
  {
    return new ByteArrayInputStream(bytes);
  }

  private static ImageUtils fromImageFile(File file)
    throws IOException
  {
    ImageUtils utils = new ImageUtils();
    utils.load(file);
    return utils;
  }

  public static ImageUtils load(String fileName)
    throws IOException
  {
    File file = new File(fileName);
    return fromImageFile(file);
  }

  public int getImageWidth()
  {
    return this.image.getWidth();
  }

  public int getImageHeight()
  {
    return this.image.getHeight();
  }

  public static byte[] byteTobuffered(int x, int y, int tarWidth, int tarHeight, byte[] byteImage, String pictureFormat, int degree)
    throws FileNotFoundException
  {
    if ((x == 0) && (y == 0) && (tarWidth == 0) && (tarHeight == 0) && (degree == 0)) {
      return byteImage;
    }
    BufferedImage bufferedImage = byteToBufferedImage(byteImage);
    if (bufferedImage == null) {
      return byteImage;
    }
    if (degree != 0) {
      bufferedImage = rotateImage(bufferedImage, degree);
    }
    x = x < 0 ? 0 : x;
    y = y < 0 ? 0 : y;
    if (x + tarWidth > bufferedImage.getWidth()) {
      tarWidth = bufferedImage.getWidth() - x;
    }
    if (y + tarHeight > bufferedImage.getHeight()) {
      tarHeight = bufferedImage.getHeight() - y;
    }
    if ((pictureFormat == null) || (pictureFormat.equals(""))) {
      pictureFormat = "jpg";
    }
    if ((x == 0) && (y == 0) && (tarWidth == 0) && (tarHeight == 0)) {
      return bufferedImageToByte(bufferedImage, pictureFormat);
    }
    int iSrcWidth = bufferedImage.getWidth();
    int iSrcHeight = bufferedImage.getHeight();

    if ((iSrcWidth < tarWidth) || (iSrcHeight < tarHeight)) {
      throw new RuntimeException("Source image size too small.");
    }

    double dSrcScale = iSrcWidth * 1.0D / iSrcHeight;
    double dDstScale = tarWidth * 1.0D / tarHeight;
    int iDstHeight;
    int iDstWidth;
    if (dDstScale > dSrcScale) {
      iDstWidth = iSrcWidth;
      iDstHeight = (int)(iDstWidth * 1.0D / dDstScale);
    } else {
      iDstHeight = iSrcHeight;
      iDstWidth = (int)(iDstHeight * dDstScale);
    }

    bufferedImage = bufferedImage.getSubimage(x, y, tarWidth, tarHeight);
    return bufferedImageToByte(bufferedImage, pictureFormat);
  }

  public static BufferedImage byteToBufferedImage(byte[] bytes) {
    if (bytes == null)
      try {
        throw new FileNotFoundException("image file not be load.please execute 'load' function agin.");
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();

        return null;
      }
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    BufferedImage image1 = null;
    try {
      image1 = ImageIO.read(in);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return image1;
  }

  public static byte[] bufferedImageToByte(BufferedImage bufferedImage, String pictureFormat) {
    if (bufferedImage == null) {
      try {
        throw new FileNotFoundException("image file not be load.please execute 'load' function agin.");
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      ImageIO.write(bufferedImage, pictureFormat, out);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return out.toByteArray();
  }

  public static BufferedImage rotateImage(BufferedImage bufferedimage, int degree)
  {
    int w = bufferedimage.getWidth();
    int h = bufferedimage.getHeight();
    int cernterPointX = 0;
    int cernterPointY = 0;
    if (Math.abs(degree % 180) == 90) {
      int l = w;
      w = h;
      h = l;
      cernterPointX = (w - h) / 2;
      cernterPointY = (h - w) / 2;
    } else {
      cernterPointX = 0;
      cernterPointY = 0;
    }
    int type = bufferedimage.getColorModel().getTransparency();
    BufferedImage img;
    Graphics2D graphics2d;
    (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
    graphics2d.drawImage(bufferedimage, cernterPointX, cernterPointY, null);
    graphics2d.dispose();
    return img;
  }

  public static byte[] bufferedImageCompressTobytes(InputStream inputStream, String pictureFormat, float quality)
    throws IOException
  {
    BufferedImage image = null;
    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] bytes = null;
    try {
      int rc = -1;
      byte[] buff = new byte[1024];
      while ((rc = inputStream.read(buff)) != -1) {
        swapStream.write(buff, 0, rc);
      }
      bytes = swapStream.toByteArray();
      if ((pictureFormat == null) || (pictureFormat.equals(""))) {
        pictureFormat = "jpg";
      }
      image = byteToBufferedImage(bytes);

      if (image == null) {
        return null;
      }

      Iterator iter = ImageIO.getImageWritersByFormatName(pictureFormat);

      ImageWriter writer = (ImageWriter)iter.next();

      ImageWriteParam iwp = writer.getDefaultWriteParam();
      iwp.setCompressionMode(2);
      iwp.setCompressionQuality(quality);

      iwp.setProgressiveMode(0);

      ColorModel colorModel = ColorModel.getRGBdefault();

      iwp.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

      IIOImage iIamge = new IIOImage(image, null, null);

      writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));

      writer.write(null, iIamge, iwp);
      bytes = byteArrayOutputStream.toByteArray();
      swapStream.flush();
      byteArrayOutputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (swapStream != null) {
        swapStream.close();
        swapStream = null;
      }
      if (byteArrayOutputStream != null) {
        byteArrayOutputStream.close();
        byteArrayOutputStream = null;
      }
    }
    return bytes;
  }

  public static boolean judgeImageType(String pictureFormat)
  {
    boolean bool = false;
    if (IMAGE_TYPE.indexOf(pictureFormat) > -1) {
      bool = true;
    }
    return bool;
  }

  public static void main(String[] args)
  {
    zoomGenerateImage("D:/1.JPG", "D:/suofang.jpg", 1748, 1252, "jpg");
  }
}