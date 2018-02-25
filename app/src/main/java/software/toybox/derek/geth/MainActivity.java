package software.toybox.derek.geth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ethereum.geth.Account;
import org.ethereum.geth.EthereumClient;
import org.ethereum.geth.Geth;
import org.ethereum.geth.KeyStore;
import org.ethereum.geth.Node;
import org.ethereum.geth.NodeConfig;
import org.ethereum.geth.NodeInfo;

public class MainActivity extends AppCompatActivity {

    private TextView accDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accDisplayTextView = (TextView) findViewById(R.id.acc_display_textview);

        try {
            NodeConfig nc = new NodeConfig();
            Node node = Geth.newNode(getFilesDir() + "/.ethNode", nc);
            node.start();

            GethNodeHolder gethNode = GethNodeHolder.getInstance();
            gethNode.setNode(node);

        } catch (Exception e) {
            Log.e("error: ", e.getMessage());
            e.printStackTrace();
        }
    }

    public void createAccount(View button) {

        try {
            GethNodeHolder gethNodeHolder = GethNodeHolder.getInstance();
            Node gethNode = gethNodeHolder.getNode();

            if (gethNode != null) {

                KeyStore ks = new KeyStore(getFilesDir() + "/keystore", Geth.LightScryptN, Geth.LightScryptP);
                Account newAccount = ks.newAccount("Password");

                Log.d("account address: ", newAccount.getAddress().getHex());
                gethNodeHolder.setAccount(newAccount);

                Account account = gethNodeHolder.getAccount();
                accDisplayTextView.setText("Here is your Account Address: " + account.getAddress().getHex());
            }
        } catch (Exception e) {
            Log.d("error: ", e.getMessage());
            e.printStackTrace();
        }
    }
}
