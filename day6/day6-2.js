const fs = require('fs');

const filename = process.argv[2];
const input = fs.readFileSync(filename, 'utf-8').trim();
const lines = input.split('\n');

let problems = [];
let i = 0;
let operands = [];
let operator = '';
let col = [];

do {
    col = lines.map(line => line.charAt(i));
//    console.log(col);
    if (col.slice(-1)[0].trim() != '') operator = col.slice(-1);
    if (col.slice(0,-1).join('').trim() == '') {
        problems.push(operands.join(operator));
        operands = [];
    } else {
        operands.push(col.slice(0,-1).join(''));
    }
    i++;
} while (col.some(c => c != ''));
//console.log(problems);


const total = problems.map(p => eval(p)).reduce((acc, val) => acc + val, 0);
console.log(total);
