package com.mrburgerus.betaplus.beta.noise;

import java.util.Random;

public class NoiseGeneratorBasic
{
	private static final double DOUBLE_ROOT3_2 = 0.5D * (Math.sqrt(3.0D) - 1.0D);
	private static final double DOUBLE_ROOT3_6 = (3.0D - Math.sqrt(3.0D)) / 6.0D;
	private static int[][] intPlot = {{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}};
	public double noiseField1;
	public double noiseField2;
	public double noiseField3;
	private int[] ints512;

	public NoiseGeneratorBasic()
	{
		this(new Random());
	}

	public NoiseGeneratorBasic(Random random)
	{
		ints512 = new int[512];
		noiseField1 = (random.nextDouble() * 256.0D);
		noiseField2 = (random.nextDouble() * 256.0D);
		noiseField3 = (random.nextDouble() * 256.0D);
		for (int i = 0; i < 256; ints512[i] = (i++))
		{
		}
		for (int i = 0; i < 256; i++)
		{
			int nextRand = random.nextInt(256 - i) + i;
			int selected = ints512[i];
			ints512[i] = ints512[nextRand];
			ints512[nextRand] = selected;
			ints512[(i + 256)] = ints512[i];
		}
	}

	private static int wrap(double doubleIn)
	{
		return doubleIn > 0.0D ? (int) doubleIn : (int) doubleIn - 1;
	}

	private static double sumFirstTwoMult(int[] ints, double mult1, double mult2)
	{
		return ints[0] * mult1 + ints[1] * mult2;
	}

	public void noiseBounder1(double[] doubles, double dNoise1, double dNoise2, int iBound, int jBound, double iNoise1, double nMult1, double nMult2)
	{
		int zeroVal = 0;
		for (int i = 0; i < iBound; i++)
		{
			double multiplier1 = (dNoise1 + i) * iNoise1 + noiseField1;
			for (int j = 0; j < jBound; j++)
			{
				double var19 = (dNoise2 + j) * nMult1 + noiseField2;
				double var27 = (multiplier1 + var19) * DOUBLE_ROOT3_2;
				int var29 = wrap(multiplier1 + var27);
				int var30 = wrap(var19 + var27);
				double var31 = (var29 + var30) * DOUBLE_ROOT3_6;
				double var33 = var29 - var31;
				double var35 = var30 - var31;
				double var37 = multiplier1 - var33;
				double var39 = var19 - var35;
				byte var42;
				byte var41;
				if (var37 > var39)
				{
					var41 = 1;
					var42 = 0;
				}
				else
				{
					var41 = 0;
					var42 = 1;
				}
				double var43 = var37 - var41 + DOUBLE_ROOT3_6;
				double var45 = var39 - var42 + DOUBLE_ROOT3_6;
				double var47 = var37 - 1.0D + 2.0D * DOUBLE_ROOT3_6;
				double var49 = var39 - 1.0D + 2.0D * DOUBLE_ROOT3_6;
				int var51 = var29 & 0xFF;
				int var52 = var30 & 0xFF;
				int var53 = ints512[(var51 + ints512[var52])] % 12;
				int var54 = ints512[(var51 + var41 + ints512[(var52 + var42)])] % 12;
				int var55 = ints512[(var51 + 1 + ints512[(var52 + 1)])] % 12;
				double var56 = 0.5D - var37 * var37 - var39 * var39;
				double var21;
				if (var56 < 0.0D)
				{
					var21 = 0.0D;
				}
				else
				{
					var56 *= var56;
					var21 = var56 * var56 * sumFirstTwoMult(intPlot[var53], var37, var39);
				}
				double var58 = 0.5D - var43 * var43 - var45 * var45;
				double var23;
				if (var58 < 0.0D)
				{
					var23 = 0.0D;
				}
				else
				{
					var58 *= var58;
					var23 = var58 * var58 * sumFirstTwoMult(intPlot[var54], var43, var45);
				}
				double var60 = 0.5D - var47 * var47 - var49 * var49;
				double var25;
				if (var60 < 0.0D)
				{
					var25 = 0.0D;
				}
				else
				{
					var60 *= var60;
					var25 = var60 * var60 * sumFirstTwoMult(intPlot[var55], var47, var49);
				}
				int pos1 = zeroVal++;
				doubles[pos1] += 70.0D * (var21 + var23 + var25) * nMult2;
			}
		}
	}
}
