package case06;

import java.io.IOException;
import java.util.Arrays;

public class Get {

	public static void main(String[] args) throws IOException {
		double[] rows1 = Utils.getVolume("2330", "20250131");
		double[] rows2 = Utils.getVolume("2330", "20250231");
		double[] rows3 = Utils.getVolume("2330", "20250331");
		double[] rows4 = Utils.getVolume("2330", "20250431");
		double[] rows5 = Utils.getVolume("2330", "20250531");
		double[] rows6 = Utils.getVolume("2330", "20250605");
		
		double[] merged = java.util.stream.Stream.of(rows1, rows2, rows3, rows4, rows5, rows6)
			    .flatMapToDouble(java.util.Arrays::stream)
			    .toArray();
		
		StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < merged.length; i++) {
            sb.append(String.format("%.0f", merged[i]));
            if (i < merged.length - 1) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb.toString());
		
	}

}
