package com.jaeckel.ether;

import org.ethereum.core.Account;
import org.ethereum.core.Block;
import org.ethereum.core.Wallet;
import org.ethereum.manager.WorldManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;

import static spark.Spark.before;
import static spark.Spark.get;

public class SparkMain {

    private static Logger logger = LoggerFactory.getLogger("spark");
    private static WorldManager worldManager = WorldManager.getInstance();


    public static void main(String[] args) {

        new App().keepConnection();

        before(new Filter() {
            @Override
            public void handle(Request request, Response response) {
                response.type("text/plain");
            }
        });

        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                logger.debug("/hello called");
                return "Hello World!";
            }
        });

        get(new Route("/wallet") {
            @Override
            public Object handle(Request request, Response response) {
                logger.debug("/wallet called");

                Wallet wallet = App.getEthereum().getWallet();
                logger.debug("Wallet: " + wallet);
                logger.debug("Wallet: wallet.getAccountCollection().size(): " + wallet.getAccountCollection().size());



                return dump(wallet);
            }
        });

        get(new Route("/dump") {
            @Override
            public Object handle(Request request, Response response) {
                logger.debug("/dump called");

//                worldManager.getRepository().dumpState();

                Wallet wallet = App.getEthereum().getWallet();
                logger.debug("Wallet: " + wallet);
                logger.debug("Wallet: wallet.getAccountCollection().size(): " + wallet.getAccountCollection().size());



                return dump(wallet);
            }
        });

        get(new Route("/block/:nr") {
            @Override
            public Object handle(Request request, Response response) {
                int nr = Integer.valueOf(request.params(":nr"));

               Block block =  worldManager.getRepository().getBlock(nr);

                return block.toString();
            }
        });
    }

    private static String dump(Wallet wallet) {

        StringBuffer result = new StringBuffer();

        Collection<Account> accounts = wallet.getAccountCollection();
        for (Account account : accounts) {
            result.append("address: " + Hex.toHexString(account.getAddress()) + ", ");
            result.append("balance: " + account.getAccountState().getBalance() + ", ");

        }

        return result.toString();
    }
}
