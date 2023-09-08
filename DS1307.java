// Adafruit DS1307

const adr         = 0x68; // I2C address for DS1307
const adr_control = 0x07; // Control register
const adr_NVRAM   = 0x08; // Start of RAM registers - 56 bytes, 0x08 to 0x3f

function initialize(){
    if(Clear()!=true) return "I2C bus error. Check the pull-ups.";
    return Read(adr, adr_NVRAM, 56);
}

function loop(){
    return true;
}

function finish(){
    return true;
}