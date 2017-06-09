package com.bwsw.pulse.views

import com.bwsw.pulse.config.RangeConfig

case class PermittedIntervals(shift: List[String], aggregations_allowed: List[RangeConfig]) extends View

case class ErrorView(params: Map[String, String], errors: List[String]) extends View