/*
 * Encog(tm) Examples v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.examples.neural.forest.feedforward;

import java.io.File;

public class Constant {
	
	/**
	 * The base directory that all of the data for this example is stored in.
	 */
	public static final File BASE_DIRECTORY = new File("/Users/jeff/data");
	
	/**
	 * The source data file from which all others are built.  This file can
	 * be downloaded from:
	 * 
	 * http://kdd.ics.uci.edu/databases/covertype/covertype.html
	 */
	public static final File COVER_TYPE_FILE = new File(BASE_DIRECTORY,"covtype.data");
	
	/**
	 * 75% of the data will be moved into this file to be used as training data.  The 
	 * data is still in "raw form" in this file.
	 */
	public static final File TRAINING_FILE = new File(BASE_DIRECTORY,"training.csv");
	
	/**
	 * 25% of the data will be moved into this file to be used as evaluation data.  The 
	 * data is still in "raw form" in this file.
	 */
	public static final File EVALUATE_FILE = new File(BASE_DIRECTORY,"evaluate.csv");
	
	/**
	 * We will limit the number of samples per "tree type" to 3000, this causes the data
	 * to be more balanced and will not allow one tree type to over-fit the network.
	 * The training file is narrowed and placed into this file in "raw form".
	 */
	public static final File BALANCE_FILE = new File(BASE_DIRECTORY,"balance.csv");
	
	/**
	 * The training file is normalized and placed into this file.
	 */
	public static final File NORMALIZED_FILE = new File(BASE_DIRECTORY, "normalized.csv");
	
	/**
	 * CSV files are slow to parse with because the text must be converted into numbers.
	 * The balanced file will be converted to a binary file to be used for training.
	 */
	public static final File BINARY_FILE = new File(BASE_DIRECTORY, "normalized.bin");
	
	/**
	 * The trained network and normalizer will be saved into an Encog EG file.
	 */
	public static final File TRAINED_NETWORK_FILE = new File(BASE_DIRECTORY,"forest.eg");
	
	/**
	 * The name of the network inside of the EG file.
	 */
	public static final String TRAINED_NETWORK_NAME = "forest-network";
	
	/**
	 * The name of the normalization object inside of the EG file.
	 */
	public static final String NORMALIZATION_NAME = "forest-norm";
	
	/**
	 * How many minutes to train for (console mode only)
	 */
	public static final int TRAINING_MINUTES = 10;
	
	/**
	 * How many hidden neurons to use.
	 */
	public static final int HIDDEN_COUNT = 100;
}
