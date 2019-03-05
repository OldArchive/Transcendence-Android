package global;

import org.airwirej.core.Address;
import org.airwirej.core.Coin;
import org.airwirej.core.InsufficientMoneyException;
import org.airwirej.core.Peer;
import org.airwirej.core.Sha256Hash;
import org.airwirej.core.Transaction;
import org.airwirej.core.TransactionInput;
import org.airwirej.core.TransactionOutput;
import org.airwirej.crypto.DeterministicKey;
import org.airwirej.crypto.KeyCrypter;
import org.airwirej.crypto.MnemonicException;
import org.airwirej.wallet.DeterministicKeyChain;
import org.airwirej.wallet.Wallet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import global.exceptions.UpgradeException;
import global.wrappers.InputWrapper;
import global.wrappers.TransactionWrapper;
import global.exceptions.CantSweepBalanceException;
import global.exceptions.ContactAlreadyExistException;
import global.exceptions.NoPeerConnectedException;
import org.spongycastle.crypto.params.KeyParameter;
import wallet.exceptions.InsufficientInputsException;
import wallet.exceptions.TxNotFoundException;
import wallet.exceptions.CantRestoreEncryptedWallet;

/**
 * Created by mati on 18/04/17.
 */

public interface AirWireModule {

    /**
     * Initialize the module
     */
    void start() throws IOException;

    /**
     * ...
     */
    void createWallet();

    boolean backupWallet(File backupFile, String password) throws IOException;

    /**
     *
     *
     * @param backupFile
     */
    void restoreWallet(File backupFile) throws IOException;

    void restoreWalletFromEncrypted(File file, String password) throws CantRestoreEncryptedWallet, IOException;

    void restoreWallet(List<String> mnemonic, long timestamp,boolean bip44) throws IOException, MnemonicException;

    /**
     * If the wallet already exist
     * @return
     */
    boolean isWalletCreated();

    /**
     * Return a new address.
     */
    Address getReceiveAddress();

    Address getFreshNewAddress();

    boolean isAddressUsed(Address address);

    long getAvailableBalance();

    Coin getAvailableBalanceCoin();

    Coin getUnnavailableBalanceCoin();

    boolean isWalletWatchOnly();

    BigDecimal getAvailableBalanceLocale();

    /******    Address Label          ******/

    List<AddressLabel> getContacts();

    AddressLabel getAddressLabel(String address);

    List<AddressLabel> getMyAddresses();

    void saveContact(AddressLabel addressLabel) throws ContactAlreadyExistException;

    void saveContactIfNotExist(AddressLabel addressLabel);

    void deleteAddressLabel(AddressLabel data);


    /******   End Address Label          ******/


    boolean chechAddress(String addressBase58);

    Transaction buildSendTx(String addressBase58, Coin amount, String memo,Address changeAddress) throws InsufficientMoneyException;
    Transaction buildSendTx(String addressBase58, Coin amount,Coin feePerKb, String memo,Address changeAddress) throws InsufficientMoneyException;

    WalletConfiguration getConf();

    List<TransactionWrapper> listTx();

    Coin getValueSentFromMe(Transaction transaction, boolean excludeChangeAddress);

    void commitTx(Transaction transaction);

    List<Peer> listConnectedPeers();

    int getChainHeight();

    AirWireRate getRate(String selectedRateCoin);

    List<InputWrapper> listUnspentWrappers();

    Set<InputWrapper> convertFrom(List<TransactionInput> list) throws TxNotFoundException;

    Transaction getTx(Sha256Hash txId);

    List<String> getMnemonic();

    String getWatchingPubKey();
    DeterministicKey getWatchingKey();

    DeterministicKey getKeyPairForAddress(Address address);

    TransactionOutput getUnspent(Sha256Hash parentTxHash, int index) throws TxNotFoundException;

    List<TransactionOutput> getRandomUnspentNotInListToFullCoins(List<TransactionInput> inputs, Coin amount) throws InsufficientInputsException;

    Transaction completeTx(Transaction transaction,Address changeAddress,Coin fee) throws InsufficientMoneyException;
    Transaction completeTx(Transaction transaction) throws InsufficientMoneyException;

    Transaction completeTxWithCustomFee(Transaction transaction,Coin fee) throws InsufficientMoneyException;

    Coin getUnspentValue(Sha256Hash parentTransactionHash, int index);

    boolean isAnyPeerConnected();

    long getConnectedPeerHeight();

    int getProtocolVersion();

    void checkMnemonic(List<String> mnemonic) throws MnemonicException;

    boolean isSyncWithNode() throws NoPeerConnectedException;

    void watchOnlyMode(String xpub, DeterministicKeyChain.KeyChainType keyChainType) throws IOException;

    boolean isBip32Wallet();

    boolean sweepBalanceToNewSchema() throws InsufficientMoneyException, CantSweepBalanceException;

    boolean upgradeWallet(String upgradeCode) throws UpgradeException;

    List<AirWireRate> listRates();

    List<String> getAvailableMnemonicWordsList();

    /**
     * Encrypt the wallet
     * @param password
     * @return
     */
    boolean encrypt(String password) throws UnsupportedEncodingException;
    boolean decrypt(String password) throws UnsupportedEncodingException;
    boolean isWalletLocked();
}
