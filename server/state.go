package server

type Status struct {
	LeftWindowUp       bool `json:"leftWindowUp"`
	LeftWindowDown     bool `json:"leftWindowDown"`
	RightWindowUp      bool `json:"rightWindowUp"`
	RightWindowDown    bool `json:"rightWindowDown"`
	LeftDoorLocked     bool `json:"leftDoorLocked"`
	RightDoorLocket    bool `json:"rightDoorLocked"`
	TrunkOpen          bool `json:"trunkOpen"`
	HazardOn           bool `json:"hazardOn"`
	PopupOn            bool `json:"popupOn"`
	HornOn             bool `json:"hornOn"`
	HornStrobe         bool `json:"hornStrobe"`
	McACOn             bool `json:"mcACOn"`
	McFanOn            bool `json:"mcFanOn"`
	McAccessoryOn      bool `json:"mcAccessoryOn"`
	CarAccessoryOn     bool `json:"carAccessoryOn"`
	McIG1On            bool `json:"mcIG1On"`
	CarIG1On           bool `json:"carIG1On"`
	McIG2On            bool `json:"mcIG2On"`
	CarIG2On           bool `json:"carIG2On"`
	BrakeSwitch        bool `json:"brakeSwitch"`
	ClutchSwitch       bool `json:"clutchSwitch"`
	ParkingBrake       bool `json:"parkingBrake"`
	InReverse          bool `json:"inReverse"`
	InNeutral          bool `json:"inNeutral"`
	EngineRunning      bool `json:"engineRunning"`
	BluetoothConnected bool `json:"bluetoothConnected"`
	CarStarted         bool `json:"carStarted"`
	AuxInput           bool `json:"auxInput"`
}
