// Pmod ACL - ADXL345

const adr = 0x1D;
var rgG = [];

function initialize(){
    if(Clear()!=true) return "I2C bus error. Check the pull-ups.";
    if(!Write(adr, 0x2D, 0x08)) return "Communication error."; // POWER_CTL | Measure
    if(!Write(adr, 0x31, 0x08)) return "Communication error." // DATA_FORMAT | FULL_RES
    
    wait(0.1);
    rgG = [];
    // write file header
    if(!FileWriteLine("~/Desktop/default.csv", ["G", "X", "Y", "Z"])) return "File write failed";
    return true;
}
function data2g(msb, lsb){
    // MSBit sign, LSBbit 0.004g 
    return 1.0* ((msb<<24) | (lsb<<16)) /256/256/256;
}
function loop(){
    var rg = Read(adr, 0xF2, 6);
    // convert data bits to signed value relative to gravitational constant 
    var x = data2g(rg[1], rg[0]);
    var y = data2g(rg[3], rg[2]);
    var z = data2g(rg[5], rg[4]);
    // total acceleration
    var s = sqrt(sqr(x)+sqr(y)+sqr(z));
    if(!FileAppendLine("~/Desktop/default.csv", [s, x, y, z])) return "File write failed";
    rgG.push(s);
    return true;
}
function finish(){
    var g = 0;
    rgG.forEach(function(v){g+=v;})
    g /= rgG.length;
    return "Average acceleration: "+g+"g";
}