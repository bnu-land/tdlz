package cn.edu.bnu.land.model;
import java.util.HashMap;

public class AHP {

	static int n;// 鐭╅樀鐨勯樁鏁�
	double cal[][];// 灞傛姣旇緝鐭╅樀,褰掍竴鍖栧悗鐨�
	double cal1[][];// 灞傛姣旇緝鐭╅樀
	static HashMap hm = null;// 灏�2闃剁殑鎴愬姣旇緝鐭╅樀鐨勫钩鍧囬殢鏈轰竴鑷存�鎸囨爣鍊间繚瀛樺湪鍝堝笇鏄犲皠琛ㄩ噷

	// 璁剧疆骞冲潎闅忔満涓�嚧鎬ф寚鏍囧�
	public static void setHm() {
		hm = new HashMap();
		hm.put(1, 0);
		hm.put(2, 0);
		hm.put(3, 0.58);
		hm.put(4, 0.89);
		hm.put(5, 1.12);
		hm.put(6, 1.24);
		hm.put(7, 1.32);
		hm.put(8, 1.41);
		hm.put(9, 1.45);
		hm.put(10, 1.49);
		hm.put(11, 1.52);
		hm.put(12, 1.54);
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	// 鍘熷鎴愬姣旇緝鐭╅樀鍒楀悜閲忕殑褰掍竴鍖�
	public void colvectortoone(double arr[])// 鍒楀悜閲忕殑褰掍竴鍖�
	{
		arr = new double[n];
//		System.out.println("姣忓垪姹傚姞鍜屽紑濮�);
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				arr[j] += cal[i][j];// 姣忓垪姹傚姞鍜�
			}
		}
//		System.out.println("姣忓垪姹傚姞鍜屽畬姣�);
//		System.out.println("鎵撳嵃琛屽�鍜岃繘琛岄獙璇佸紑濮�);
		for (int i = 0; i < n; i++) {
			System.out.println(arr[i]);// 鎵撳嵃琛屽�鍜岃繘琛岄獙璇�
		}
//		System.out.println("鎵撳嵃琛屽�鍜岃繘琛岄獙璇佸畬姣�);
		System.out.println("鍒楀悜閲忓綊涓�寲寮�");
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				cal[i][j] = cal[i][j] / arr[j];// 鍒楀悜閲忓綊涓�寲
			}
		}
		System.out.println("鍒楀悜閲忓綊涓�寲瀹屾瘯");
	//	System.out.println("鎵撳嵃褰掍竴鍖栧悗鐨勬暟缁勫紑濮�);
		for (int j = 0; j < n; j++)// 鎵撳嵃褰掍竴鍖栧悗鐨勬暟缁�
		{
			for (int i = 0; i < n; i++) {
				System.out.print(cal[j][i] + "\t");// 鍒楀悜閲忓綊涓�寲
			}
			System.out.println();
		}
	//	System.out.println("鎵撳嵃褰掍竴鍖栧悗鐨勬暟缁勭粨鏉�);
	}

	// 寰楀埌褰掍竴鍖栫煩闃靛悇琛岀殑琛屽拰
	public double[] rowsum(double arr1[])// 鎸夎姹傚拰,骞惰繑鍥炴眰寰楃殑鏁扮粍
	{
		arr1 = new double[n];
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				arr1[j] += cal[j][i];// 姣忚姹傚姞鍜�
			}
		}
		System.out.println("琛屽拰鎵撳嵃楠岃瘉寮�");
		for (int j = 0; j < n; j++) {
			System.out.println(arr1[j]);
		}
		System.out.println("琛屽拰鎵撳嵃楠岃瘉缁撴潫");
		// 浠ヤ笅瀵筧rr1[]杩涜褰掍竴鍖�
		double sum = 0;

		for (int i = 0; i < n; i++) {
			sum += arr1[i];
		}
//		System.out.println("鍚戦噺褰掍竴鍖栬绠楀紑濮�);
		for (int i = 0; i < n; i++)// 寰楀埌鏉冮噸
		{
			arr1[i] = arr1[i] / sum;
		}
//		System.out.println("鍚戦噺褰掍竴鍖栬绠楃粨鏉�);
		System.out.println("鎵撳嵃鏉冮噸寮�");
		for (int j = 0; j < n; j++) {
			System.out.println(arr1[j]);
		}
		System.out.println("鎵撳嵃鏉冮噸缁撴潫");
		return arr1;
	}

	// 姹傛渶澶х壒寰佸�
	public double getnamda(double arr1[], double arr2[]) {
		arr2 = new double[n];
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				arr2[j] += cal1[j][i] * arr1[i];// 寰楀埌A*w
			}
		}
		System.out.println("鎵撳嵃A*w寮�");
		for (int i = 0; i < n; i++) {
			System.out.println(arr2[i]);// 鎵撳嵃鏉冮噸鍚戦噺
		}
		System.out.println("鎵撳嵃A*w缁撴潫");
		double L = 0;// 5*namda
		for (int i = 0; i < n; i++) {
			L += arr2[i] / arr1[i];
		}
		double namda = L / n;
		return namda;
	}
}
