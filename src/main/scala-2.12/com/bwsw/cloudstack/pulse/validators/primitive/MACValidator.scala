package com.bwsw.cloudstack.pulse.validators.primitive

import com.bwsw.cloudstack.pulse.validators.PatternValidator

class MACValidator(field: String) extends PatternValidator(field) {
  override protected def getPattern = "^([0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2})$".r
}