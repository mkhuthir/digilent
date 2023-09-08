// Pmod GYRO - L3G4200D

const adr = 0x69;

function initialize(){
    if(Clear()!=true) return "I2C bus error. Check the pull-ups.";
    if(Write(adr)!=true) return "Device NAK";
    if(Read(adr, 0x0F, 1) != 0xD3) return "Device ID mismatch";
    if(Write(adr, 0x20, 0x0F)!=0) return "Communication error";  // CTRL_REG1
    if(!FileWriteLine("~/Desktop/default.csv", ["dps", "X", "Y", "Z"])) return "File write failed";
    return true;
}

function loop(){
    var rg = Read(adr, 0xA8, 6); // DATA 

    // convert data bits to signed value 250 dps full scale 
    var x = 0.004* ((rg[1]<<24) | (rg[0]<<16)) /256/256;
    var y = 0.004* ((rg[3]<<24) | (rg[2]<<16)) /256/256;
    var z = 0.004* ((rg[5]<<24) | (rg[4]<<16)) /256/256;
    
    // total acceleration
    var s = sqrt(sqr(x)+sqr(y)+sqr(z));
    if(!FileAppendLine("~/Desktop/default.csv", [s, x, y, z])) return "File write failed";
    return true;
}
function finish(){
    return "done";
}