const fs = require('fs');

const filename = process.argv[2];
const input = fs.readFileSync(filename, 'utf-8').trim();

let problems = [];

input.split('\n').forEach((line, index) => {
    problems.push(line.trim().split(/\s+/));
});

problems = problems[0].map((_, colIndex) => problems.map(row => row[colIndex]));

const total = problems.map(p => eval(p.slice(0,-1).join(p.slice(-1)[0]))).reduce((total, val) => total + val, 0);

console.log(total);

