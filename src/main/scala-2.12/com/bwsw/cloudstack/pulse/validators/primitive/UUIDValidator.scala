package com.bwsw.cloudstack.pulse.validators.primitive

import com.bwsw.cloudstack.pulse.validators.PatternValidator

class UUIDValidator(field: String) extends PatternValidator(field) {
  override protected def getPattern = "^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})$".r
}
