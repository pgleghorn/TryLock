package com.oracle.support;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

public class TryLock {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "test" + new Date().getTime();
		
		if (args.length > 0) {
			filename = args[0];
		}

		File f = new File(filename);
		System.out.println("Trying to lock " + f.getAbsolutePath());
		
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "rws");
			
			FileChannel fc = raf.getChannel();

			Date t1 = new Date();
			FileLock fl = fc.tryLock();
			Date t2 = new Date();
			System.out.println("java.nio.channels.FileChannel.tryLock() took "
					+ (t2.getTime() - t1.getTime()) + " ms");
			if (fl == null) {
				System.out.println("Lock failed");
			} else {
				System.out.println("  toString: " + fl.toString());
				System.out.println("  isValid: " + fl.isValid());
				System.out.println("  size: " + fl.size());
				System.out.println("  isShared: " + fl.isShared());
				System.out.println("  position: " + fl.position());
				System.out.println("  acquiredBy: " + fl.acquiredBy().toString());
				fl.release();
			}
			raf.close();
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

}
