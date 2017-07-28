package com.bwsw.cloudstack.pulse.validators.primitive

import com.bwsw.cloudstack.pulse.validators.PatternValidator

class TimeFrameValidator(field: String) extends PatternValidator(field) {
  override protected def getPattern = "^([0-9]+[mhd])$".r
}