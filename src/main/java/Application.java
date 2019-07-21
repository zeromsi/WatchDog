import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {
		String CMD_clusterLogin = "oc login https://console.ip.nip.io:8443 -u= -p=";
		String CMD_metrics = "oc adm top node etcd.ngfs.com";
		Process loginToCluster = Runtime.getRuntime().exec(CMD_clusterLogin);
		loginToCluster.waitFor();
		while (true) {
			Process getMetrics = Runtime.getRuntime().exec(CMD_metrics);
			BufferedReader reader = new BufferedReader(new InputStreamReader(getMetrics.getInputStream()));
			reader.readLine();
			String line = reader.readLine();

			try {
				int lastIndexOfPercentage = line.lastIndexOf('%');
				int memoryUsages = Integer.parseInt(line.substring(lastIndexOfPercentage - 2, lastIndexOfPercentage));
				if (memoryUsages > 90) {
					Runtime.getRuntime().exec("sshpass -p Open@1234 ssh root@ip1 init 6 ");
					Runtime.getRuntime().exec("sshpass -p Open@1234 ssh root@ip2 init 6 ");
					Runtime.getRuntime().exec("sshpass -p Open@1234 ssh root@ip3 init 6 ");
				}
			} catch (Exception e) {
			}
			System.out.println("Server is on good mode");
			Thread.sleep(10000 * 60 * 5);
		}

	}
}
