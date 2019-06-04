package org.tron.core.db.api;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.utils.ByteArray;
import org.tron.core.capsule.PedersenHashCapsule;
import org.tron.core.db.Manager;
import org.tron.core.zen.merkle.IncrementalMerkleTreeContainer;

@Slf4j(topic = "DB")

public class ZkPressureTestPreparer {

  private Manager dbManager;

  private long zkTransactionNum = 20_000L;

  private PedersenHash cmHash = null;

  public ZkPressureTestPreparer(Manager dbManager) {
    this.dbManager = dbManager;
  }

  public void init() {
    byte[] bytes = ByteArray
        .fromHexString("9554a9a5d3085216aa1753610c2cc1d21f9005416c377aa3f0c5a4fc575eba15");
    PedersenHashCapsule compressCapsule1 = new PedersenHashCapsule();
    compressCapsule1.setContent(ByteString.copyFrom(bytes));
    cmHash = compressCapsule1.getInstance();

  }

  public void doWork() {
    logger.info("Start to prepare shield env");
    long start = System.currentTimeMillis();
    init();
    try {
      prepareShieldEnvironment();
    } catch (Exception ex) {
      throw new RuntimeException("prepare shield error ", ex);
    }

    logger.info("Preparing shield env costs :" + (System.currentTimeMillis() - start));
    logger.info("End");
  }

  private void prepareShieldEnvironment() throws Exception {

    long cmNum = zkTransactionNum;

    // update merkleStore
    IncrementalMerkleTreeContainer bestMerkle = dbManager.getMerkleContainer().getBestMerkle();
    logger.info("bestMerkle.size:" + bestMerkle.size());

    for (int i = 0; i < cmNum; i++) {
      bestMerkle.append(cmHash);
    }
    dbManager.getMerkleContainer().setBestMerkle(0, bestMerkle);

    String merkleRoot = ByteArray.toHexString(bestMerkle.getMerkleTreeKey());
    logger.info("merkleRoot:" + merkleRoot);

  }

}
