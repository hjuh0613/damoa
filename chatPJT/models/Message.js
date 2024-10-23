const mongoose = require('mongoose');
const { Schema } = mongoose;

const messageSchema = new Schema({
    userNo: { type: String, required: true },
    userNickname: { type: String, required: true },
    roomNo: { type: String, required: true },
    isFile: { type: Boolean, default: false},
    msg: { type: String, required: true },
    regDate: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Message', messageSchema);