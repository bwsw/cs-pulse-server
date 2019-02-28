package com.bwsw.cloudstack.pulse.views

import com.bwsw.cloudstack.pulse.config.ScaleConfig

case class PermittedIntervals(shifts: List[String], scales: List[(String, ScaleConfig)]) extends View

case class ErrorView(params: Map[String, String], errors: List[String]) extends View