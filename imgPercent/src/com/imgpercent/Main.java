package com.imgpercent;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Contabiliza a quantidade de pixels na imagem do brasil.
 * Exporta uma nova versão da imagem para verificar visualmente o processo de contagem.
 * @author Arthur.
 *
 */
public class Main {
	
	public static void main(String[] args) {
		BufferedImage img = null;
		BufferedImage imgOriginal = null;
		try {
		    img = ImageIO.read(new File("brasil.jpeg"));
		    imgOriginal = ImageIO.read(new File("brasil.jpeg"));
		    int w = img.getWidth();
		    int h = img.getHeight();
		    int total = w*h;
		    
		    float pixelsBrancos = 0;
		    float pixelsAmarelos = 0;
		    float pixelsVerdes = 0;
		    float pixelsVermelhos = 0;
		    float pixelsAzuis = 0;
		    float pixelsCyan = 0;
		    
		    
		    for(int i = 0; i < w; i++) {
		    	for (int j = 0; j < h; j++) {
		    		int rgb = img.getRGB(i, j);
		    		
		    		//FILTRANDO A VARIAÇÃO DE COR E CONTANDO A QUANTIDADE DE CADA PIXEL.
		    		if (isBranco(rgb)) {
		    			pixelsBrancos++;
		    		}
		    		else if (isAmarelo(rgb)) {
		    			img.setRGB(i, j, Color.GREEN.getRGB());
		    			pixelsAmarelos++;
		    		}
		    		else if (isVerde(rgb)) {
		    			img.setRGB(i, j, Color.RED.getRGB());
		    			pixelsVerdes++;
		    		}
		    		else if (isVermelho(rgb)) {
		    			img.setRGB(i, j, Color.BLUE.getRGB());
		    			pixelsVermelhos++;
		    		}
		    		else if (isAzul(rgb)) {
		    			img.setRGB(i, j, Color.CYAN.getRGB());
		    			pixelsAzuis++;
		    		}
		    		else if (isCyan(rgb)) {
		    			img.setRGB(i, j, Color.YELLOW.getRGB());
		    			pixelsCyan++;
		    		}
		    		//DECIDINDO A COR DOS PIXELS RESTANTES (BORDAS PRETAS E CORES COM POUCA SATURAÇÃO)
		    		else {
		    			int pxAmarelosRelat = 0;
					    int pxVerdesRelat = 0;
					    int pxVermelhosRelat = 0;
					    int pxAzuisRelat = 0;
					    int pxCyanRelat = 0;
					    int lowerBoundWidth = Math.max(i-3, 0);
					    int upperBoundWidth = Math.min(i+3, w);
					    int lowerBoundHeight = Math.max(j-3, 0);
					    int upperBoundHeight = Math.min(j+3, h);
		    			for(int x = lowerBoundWidth; x < upperBoundWidth; x++) {
		    				for (int y = lowerBoundHeight; y < upperBoundHeight; y++) {
		    					int rgbRelat = imgOriginal.getRGB(x, y);
		    					
		    					if (isAmarelo(rgbRelat)) {
		    		    			pxAmarelosRelat++;
		    		    		}
		    		    		else if (isVerde(rgbRelat)) {
		    		    			pxVerdesRelat++;
		    		    		}
		    		    		else if (isVermelho(rgbRelat)) {
		    		    			pxVermelhosRelat++;
		    		    		}
		    		    		else if (isAzul(rgbRelat)) {
		    		    			pxAzuisRelat++;
		    		    		}
		    		    		else if (isCyan(rgbRelat)) {
		    		    			pxCyanRelat++;
		    		    		}
		    				}
		    			}
		    			if (pxAmarelosRelat > pxVerdesRelat + pxVermelhosRelat + pxAzuisRelat + pxCyanRelat) {
		    				img.setRGB(i, j, Color.GREEN.getRGB());
		    				pixelsAmarelos++;
		    			}
		    			else if (pxVerdesRelat > pxAmarelosRelat + pxVermelhosRelat + pxAzuisRelat + pxCyanRelat) {
		    				img.setRGB(i, j, Color.RED.getRGB());
			    			pixelsVerdes++;
		    			}
		    			else if (pxVermelhosRelat > pxVerdesRelat + pxAmarelosRelat + pxAzuisRelat + pxCyanRelat) {
		    				img.setRGB(i, j, Color.BLUE.getRGB());
			    			pixelsVermelhos++;
		    			}
		    			else if (pxAzuisRelat > pxVermelhosRelat + pxVerdesRelat + pxAmarelosRelat + pxCyanRelat) {
		    				img.setRGB(i, j, Color.CYAN.getRGB());
			    			pixelsAzuis++;
		    			}
		    			else if (pxCyanRelat > pxAzuisRelat + pxVermelhosRelat + pxVerdesRelat + pxAmarelosRelat) {
		    				img.setRGB(i, j, Color.YELLOW.getRGB());
			    			pixelsCyan++;
		    			}
		    		}
		    	}
		    }
		    System.out.println("Total: " + total);
		    System.out.println("Brancos: " + (int)pixelsBrancos);
		    System.out.println("Amarelos: " + (int)pixelsAmarelos);
		    System.out.println("Verdes: " + (int)pixelsVerdes);
		    System.out.println("Vermelhos: " + (int)pixelsVermelhos);
		    System.out.println("Azuis: " + (int)pixelsAzuis);
		    System.out.println("Cyan: " + (int)pixelsCyan);
		    
		    float somaPixels = pixelsAmarelos + pixelsVerdes + pixelsVermelhos + pixelsAzuis + pixelsCyan;
		    float areaBrasil = (somaPixels / total) * 100;
		    float areaNorte = (pixelsAmarelos / total) * 100;
		    float areaNordeste = (pixelsVerdes / total) * 100;
		    float areaCentroOeste = (pixelsVermelhos / total) * 100;
		    float areaSudeste = (pixelsAzuis / total) * 100;
		    float areaSul = (pixelsCyan / total) * 100;
		    int naoContabilizados = (int) (total - (somaPixels + pixelsBrancos));
		    float areaNaoContabilizada = ((float)naoContabilizados / total) * 100;
		    System.out.println("Pixels não contabilizados: " + naoContabilizados + " (" + areaNaoContabilizada + "%)");
		    System.out.println("Área do Norte: " + areaNorte + "%");
		    System.out.println("Área do Nordeste: " + areaNordeste + "%");
		    System.out.println("Área do Centro-oeste: " + areaCentroOeste + "%");
		    System.out.println("Área do Sudeste: " + areaSudeste + "%");
		    System.out.println("Área do Sul: " + areaSul + "%");
		    System.out.println("Área total do Brasil: " + areaBrasil + "%");
		    
		    File file = new File("output.jpeg");
		    ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
		}
	}
	private static boolean isBranco(int rgb) {
		Color pxColor = new Color(rgb);
		return pxColor.getRed() > 240 && pxColor.getGreen() > 240 && pxColor.getBlue() > 240;
	}
	private static boolean isAmarelo(int rgb) {
		Color pxColor = new Color(rgb);
		return pxColor.getRed() > 200 && pxColor.getGreen() > 200 && pxColor.getBlue() < 70;
	}
	private static boolean isVerde(int rgb) {
		Color pxColor = new Color(rgb);
		return pxColor.getRed() < 60 && pxColor.getGreen() > 100 && pxColor.getBlue() < 70;
	}
	private static boolean isVermelho(int rgb) {
		Color pxColor = new Color(rgb);
		return pxColor.getRed() > 130 && pxColor.getGreen() < 60 && pxColor.getBlue() < 60;
	}
	private static boolean isAzul(int rgb) {
		Color pxColor = new Color(rgb);
		return pxColor.getRed() < 100 && pxColor.getGreen() < 110 && pxColor.getBlue() > 130;
	}
	private static boolean isCyan(int rgb) {
		Color pxColor = new Color(rgb);
		return pxColor.getRed() < 90 && pxColor.getGreen() > 170 && pxColor.getBlue() > 170;
	}

}
