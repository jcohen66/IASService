package actors

import deathwatch.Reaper

/**
  * Created by jcohen66 on 3/28/16.
  */
class ProductionReaper extends Reaper {

  // Shutdown
  def allSoulsReaped(): Unit = context.system.shutdown()

}
