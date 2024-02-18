require('dotenv').config();
const amqp = require('amqplib')

const fila = 'PRECO'

amqp.connect({
  host: process.env.RABBITMQ_HOST,
  port: process.env.RABBITMQ_PORT,
  username: process.env.RABBITMQ_USERNAME,
  password: process.env.RABBITMQ_PASSWORD
})
    .then((conexao) => {
      conexao.createChannel()
          .then((canal) => {
            canal.consume(fila, (mensagem) => {
              console.log(mensagem.content.toString())
            }, {noAck: true}) //Confirmação de leitura da mensagem
          })
          .catch((erro) => console.log(erro))
    })
.catch((erro) => console.log(erro))