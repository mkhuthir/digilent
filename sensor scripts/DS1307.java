// Adafruit DS1307

const adr     = 0x68; // I2C address for DS1307
const control = 0x07; // Control register
const NVRAM   = 0x08; // Start of RAM registers - 56 bytes, 0x08 to 0x3f

function initialize(){
    // Process called at the beginning.
    // Like to initialize the device and storage file.

    if(Clear()!=true) return "I2C bus error. Check the pull-ups.";
    return Read(adr, NVRAM, 56);
}

function loop(){
    // Process called for specified number of times and rate.
    // Like to collect, decode and store sensor data.
    // Use only static data transfer calls!
    return true

}

function finish(){
   // Process called at the end.
   // Like to suspend the device.
   return "done";
}