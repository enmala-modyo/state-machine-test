import http from 'k6/http';
import { check } from 'k6';
/*
export const options = {
  stages: [
    {duration: '15s', target: 20},
    {duration: '30s', target: 10},
    {duration: '10s', target: 0},
  ],
  thresholds: {
    checks: ['rate>0.90']
  }
};*/
export const options = {
  vus: 50,
  duration: '10s',
  thresholds: {
    checks: ['rate>0.90']
  }
};

export default function () {
  const response = http.get('http://localhost:8080/statemachine/solicitudes');
  if (response.status === 200 && response.json().data.length > 5) {
    sendEvent(response.json().data[randomInt(response.json().data.length - 1)]);
  } else {
    const name = generateString(5, true);
    http.post('http://localhost:8080/statemachine/solicitudes?name=' + name)
  }
}

function sendEvent(solicitud) {
  let id = solicitud.id;
  let state = solicitud.state;
  let event;
  switch (state) {
    case 'SI':
      event = 'E0';
      break;
    case 'S1':
      event = 'E1';
      break;
    case 'S3':
      event = 'E4';
      break;
  }
  console.log(JSON.stringify(solicitud) + " " + event);
  const response = http.patch('http://localhost:8080/statemachine/solicitudes/' + id + '/' + event);
  check(response, {
    'verify new state is expected': (r) => {
      let verify;
      const newState = r.json().data.state;
      switch (state) {
        case 'SI': verify = newState === 'S1'; break;
        case 'S1': verify = newState === 'S3' || newState === 'SF'; break;
        case 'S3': verify = newState === 'SF'; break;
        default: true;
      }
      return verify;
    }

  })
  console.log("changed from "+state+" to "+ JSON.stringify(response.json()));
}

const letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
const numbers = '0123456789';

function generateString(length, includeNumbers) {
  let characters = letters;
  if (includeNumbers) {
    characters = characters + numbers;
  }
  let result = '';
  const charactersLength = characters.length;
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

function randomInt(limit) {
  return Math.floor(Math.random() * limit + 1)
}
