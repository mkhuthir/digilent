// Pmod CMPS - HMC5883L

const adr = 0x1E;

function initialize(){
    if(Clear()!=true) return "I2C bus error. Check the pull-ups.";
    var rgID = Read(adr, 0x0A, 3);
    if(rgID[0]!=0x48 || rgID[1]!=0x34 || rgID[2]!=0x33) return "Device ID mismatch";
    // Mode Register, Continuous-Measurement Mode
    Write(adr, 0x02, 0);  
    if(!FileWriteLine("~/Desktop/default.csv", ["Gs", "X", "Y", "Z"])) return "File write failed";
    wait(0.1);
    return true;
}
function loop(){
    var rg = Read(adr, 0x03, 6); // DATA 
    
    // convert data bits to signed value 
    var x = 1.0* ((rg[0]<<24) | (rg[1]<<16)) /256/256/1090;
    var y = 1.0* ((rg[2]<<24) | (rg[3]<<16)) /256/256/1090;
    var z = 1.0* ((rg[4]<<24) | (rg[5]<<16)) /256/256/1090;
    var s = sqrt(sqr(x)+sqr(y)+sqr(z));
    if(!FileAppendLine("~/Desktop/default.csv", [s, x, y, z])) return "File write failed";
    return true;
}
function finish(){
    return "done";
}