# Aplicação utilizando **Mensageria** com **RabbitMQ**

**RabbitMQ** é um software de mensageria (**message-broker**) de código aberto que permite que aplicativos se comuniquem uns com os outros usando filas de mensagens.

## Sobre a Aplicação desenvolvida

Aplicação simula um **MicroServiço** de a atualização de estoque de uma loja virtual. Podemos enviar o código do produto e quantidade através do **RabbitMQ** utilizando **Java** com **Spring**. A aplicação também conta com a utilização do **Prefetch Count** e um **Error Handler**.
Através de uma aplicação **Java**, fazemos o consumo da fila de _Estoque_
Através de outra aplicação **NodeJS**, realizamos o consumo da fila de _Preço_.

### Prefetch Count

**Prefetch Count** com RabbitMQ é um limitador de mensagens de envio ao consumidor.

_Exemplo_: Em uma aplicação de envio de emails em que temos um produtor e consumidor, o produtor dispara e envia centenas de milhares de requisições para o RabbitMQ.
Como essas mensagens são gerenciadas? Existe limites?

O **Prefetch Count** serve como limitador de mensagens não confirmadas a serem enviadas a consumidores que ficará em um buffer em memória.

Já que o RabbitMQ envia as mensagens aos consumidores de forma assíncrona, então podemos estabelecer um limite com o Prefetch Count.

Essa configuração é importante pois podemos tornar o processo veloz e mais performatico.

### Error Handler

O **tratamento de exceções** junto ao RabbitMQ é muito importante! Quando uma exceção não está sendo tratada e é lançada dentro do consumo de uma fila, essa mensagem é enviada **de volta a fila**, e isso gera um **loop infinito**!

Caso uma exceção seja do tipo _FATAL_, significa que ela não deve ser retornada a fila, e deve ser rejeitada.
