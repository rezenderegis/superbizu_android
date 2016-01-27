(function (process) {
    var mockServer = require('mockserver-grunt'),
    HTTP_PORT = 1080,
    mockServerClient = require('mockserver-client').mockServerClient;
    mockServer.start_mockserver({
        serverPort: HTTP_PORT
    }).then(function(){
        mockServerClient("localhost", HTTP_PORT).mockAnyResponse({
            "httpRequest": {
                "path": "/mysale/app/teste.php"/*,
                "headers": [
                    {
                        "name": "Host",
                        "values": "qa\\.environment\\.com.*"
                    }
                ]*/
            },
            /*"httpForward": { // local machine Tomcat instance
                "host": "127.0.0.1",
                "port": 8080,
                "scheme": "HTTP"
            },
            "times": {
                "unlimited": true
            }*/
            'httpResponse': {
                'statusCode': 200,
                'body': JSON.stringify([{"ID_QUESTAO":"1","ANO_QUESTAO":"2015","NUMERO_QUESTAO":"22","DESCRICAO_QUESTAO":"Marque todas as respostas certas do mapa. Quest\u00e3o","COMANDO_QUESTAO":"Ggg","PROVA":"1","SITUACAO_QUESTAO":"1","NOME_IMAGEM_QUESTAO":"IMG-20151221-WA0000","COMENTARIO_QUESTAO":"Ggg","NOME_IMAGEM_QUESTAO_SISTEMA":"_211215_.jpg","LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"1"},{"ID_QUESTAO":"2","ANO_QUESTAO":"2015","NUMERO_QUESTAO":"2","DESCRICAO_QUESTAO":"Qual a soma de 1 + 1","COMANDO_QUESTAO":"Qual a soma de 1 + 1","PROVA":"2","SITUACAO_QUESTAO":"1","NOME_IMAGEM_QUESTAO":"IMG-20151221-WA0000","COMENTARIO_QUESTAO":"Qual a soma de 1 + 1","NOME_IMAGEM_QUESTAO_SISTEMA":"_211215_.jpg","LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"2"},{"ID_QUESTAO":"3","ANO_QUESTAO":"2015","NUMERO_QUESTAO":"2","DESCRICAO_QUESTAO":"Marque todas as respostas certas","COMANDO_QUESTAO":"Marque todas as respostas certas","PROVA":"2","SITUACAO_QUESTAO":"2","NOME_IMAGEM_QUESTAO":"IMG-20151221-WA0000","COMENTARIO_QUESTAO":"Marque todas as respostas certas","NOME_IMAGEM_QUESTAO_SISTEMA":null,"LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"1"},{"ID_QUESTAO":"4","ANO_QUESTAO":"2014","NUMERO_QUESTAO":"22","DESCRICAO_QUESTAO":"Nova","COMANDO_QUESTAO":"nova","PROVA":"1","SITUACAO_QUESTAO":"2","NOME_IMAGEM_QUESTAO":"3","COMENTARIO_QUESTAO":"nova","NOME_IMAGEM_QUESTAO_SISTEMA":"4","LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"1"},{"ID_QUESTAO":"5","ANO_QUESTAO":"2015","NUMERO_QUESTAO":"1","DESCRICAO_QUESTAO":"nova 1","COMANDO_QUESTAO":"nova 1","PROVA":"1","SITUACAO_QUESTAO":"1","NOME_IMAGEM_QUESTAO":"1","COMENTARIO_QUESTAO":"nova 1","NOME_IMAGEM_QUESTAO_SISTEMA":"1","LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"1"},{"ID_QUESTAO":"6","ANO_QUESTAO":"2016","NUMERO_QUESTAO":"2","DESCRICAO_QUESTAO":"segunda alteracao da nova 2","COMANDO_QUESTAO":"nova 2","PROVA":"1","SITUACAO_QUESTAO":"1","NOME_IMAGEM_QUESTAO":"1","COMENTARIO_QUESTAO":"nova 2","NOME_IMAGEM_QUESTAO_SISTEMA":"1","LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"1"},{"ID_QUESTAO":"7","ANO_QUESTAO":"2014","NUMERO_QUESTAO":"33","DESCRICAO_QUESTAO":"Questao da pretinha","COMANDO_QUESTAO":"questao da pretinha","PROVA":"1","SITUACAO_QUESTAO":"1","NOME_IMAGEM_QUESTAO":"1","COMENTARIO_QUESTAO":"questao da pretinha","NOME_IMAGEM_QUESTAO_SISTEMA":"1","LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"1"},{"ID_QUESTAO":"8","ANO_QUESTAO":"22","NUMERO_QUESTAO":"2","DESCRICAO_QUESTAO":"andre biha","COMANDO_QUESTAO":"andre biha","PROVA":"1","SITUACAO_QUESTAO":"1","NOME_IMAGEM_QUESTAO":"1","COMENTARIO_QUESTAO":"andre biha","NOME_IMAGEM_QUESTAO_SISTEMA":"1","LETRA_ITEM_CORRETO":"A","DIA_PROVA":"1","APLICACAO":"1"}]),
                'delay': {
                    'timeUnit': 'MILLISECONDS',
                    'value': 250
                }
            },
            'times': {
                // 'remainingTimes': 1,
                'unlimited': true
            }
        });
    });
    console.log("started on port: " + HTTP_PORT);

    // stop MockServer if Node exist abnormally
    process.on('uncaughtException', function (err) {
        console.log('uncaught exception - stopping node server: ' + JSON.stringify(err));
        mockServer.stop_mockserver();
        throw err;
    });

    // stop MockServer if Node exits normally
    process.on('exit', function () {
        console.log('exit - stopping node server');
        mockServer.stop_mockserver();
    });

    // stop MockServer when Ctrl-C is used
    process.on('SIGINT', function () {
        console.log('SIGINT - stopping node server');
        mockServer.stop_mockserver();
        process.exit(0);
    });

    // stop MockServer when a kill shell command is used
    process.on('SIGTERM', function () {
        console.log('SIGTERM - stopping node server');
        mockServer.stop_mockserver();
        process.exit(0);
    });
})(process)