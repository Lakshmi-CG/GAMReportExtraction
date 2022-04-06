package admanager.axis.v202111.reportservice.test1;

import static com.google.api.ads.common.lib.utils.Builder.DEFAULT_CONFIGURATION_FILENAME;

import java.rmi.RemoteException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.api.ads.admanager.axis.factory.AdManagerServices;
import com.google.api.ads.admanager.axis.v202111.Network;
import com.google.api.ads.admanager.axis.v202111.NetworkServiceInterface;
import com.google.api.ads.admanager.lib.client.AdManagerSession;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.conf.ConfigurationLoadException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;

@SpringBootApplication
public class Test1Application {

	
	

		public static void main(String[] args) throws com.google.api.ads.admanager.axis.v202111.ApiException, RemoteException {
			SpringApplication.run(Test1Application.class, args);
			AdManagerSession session;
			try {
				// Generate a refreshable OAuth2 credential.

				Credential oAuth2Credential =
						new OfflineCredentials.Builder()
						.forApi(Api.AD_MANAGER)
						.fromFile()
						.build()
						.generateCredential();

				// Construct a AdManagerSession.
				session =
						new AdManagerSession.Builder().fromFile().withOAuth2Credential(oAuth2Credential).build();
			} catch (ConfigurationLoadException cle) {
				System.err.printf(
						"Failed to load configuration from the %s file. Exception: %s%n",
						DEFAULT_CONFIGURATION_FILENAME, cle);
				return;
			} catch (ValidationException ve) {
				System.err.printf(
						"Invalid configuration in the %s file. Exception: %s%n",
						DEFAULT_CONFIGURATION_FILENAME, ve);
				return;
			} catch (OAuthException oe) {
				System.err.printf(
						"Failed to create OAuth credentials. Check OAuth settings in the %s file. "
								+ "Exception: %s%n",
								DEFAULT_CONFIGURATION_FILENAME, oe);
				return;
			}
			// call to Ad manager API

			AdManagerServices adManagerServices = new AdManagerServices();
			NetworkServiceInterface nsi = adManagerServices.get(session, NetworkServiceInterface.class);
			Network network = nsi.getCurrentNetwork();

			//Print the response

			System.out.printf("Found network  %s (%s)\n", network.getDisplayName(),network.getNetworkCode());

	}
}  
	