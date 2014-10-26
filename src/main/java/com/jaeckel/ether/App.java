package com.jaeckel.ether;

import org.ethereum.core.Block;
import org.ethereum.core.Transaction;
import org.ethereum.core.Wallet;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumImpl;
import org.ethereum.listener.EthereumListener;
import org.ethereum.net.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger("app");

    private static Ethereum ethereum;

    public static void main( String[] args )
    {
        logger.debug("Hello World!");

        Properties props = System.getProperties();
        props.setProperty("database.reset", "true");

        new App().keepConnection();


    }


    public void keepConnection() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ethereum = new EthereumImpl();
                ethereum.addListener(new EthereumListener() {
                    @Override
                    public void trace(String output) {
                        logger.debug("trace: " + output);
                    }

                    @Override
                    public void onBlock(Block block) {
//                logger.debug("onBlock: " + block);
                        logger.debug("onBlock: ");

                    }

                    @Override
                    public void onPreloadedBlock(Block block) {
                        logger.debug("onPreloadedBlock: " + block);

                    }

                    @Override
                    public void onRecvMessage(Message message) {
                        logger.debug("onRecvMessage: " + message);

                    }

                    @Override
                    public void onSendMessage(Message message) {
                        logger.debug("onSendMessage: " + message);

                    }

                    @Override
                    public void onPeerDisconnect(String host, long port) {
                        logger.debug("onPeerDisconnect: host: " + host + ", port: " + port);

                    }

                    @Override
                    public void onPendingTransactionsReceived(Set<Transaction> transactions) {
                        logger.debug("onPendingTransactionsReceived()");

                    }

                    @Override
                    public void onSyncDone() {
                        logger.debug("onSyncDone()");

                        Wallet wallet = ethereum.getWallet();

                        logger.debug("wallet: " + wallet);



                    }
                });
//        ethereum.connect("localhost", 30303);
//        ethereum.connect("poc-7.ethdev.com", 30303);
                ethereum.connect("185.43.109.23", 30303);
                logger.debug("connect() done.");
//        ethereum.connect("poc-6.ethdev.com", 30303);
//        ethereum.connect("54.211.14.10", 30303);
//        ethereum.isBlockchainLoading();
                ethereum.close();
            }
        }).start();
    }

    public static Ethereum getEthereum() {
        return ethereum;
    }

}
