app.factory('crud', ['$http','urls', function($http,urls){
    return{
        insertar: function( urlRest,data,sucess,error ){
            
            $http({
                method: 'POST',
                url: urls.BASE + urlRest,
                headers: {'Content-Type': 'text/plain'},
                data: data
            }).success( sucess ).error( error);            
        },
        listar: function( urlRest,data,sucess,error ){
            
            $http({
                method: 'GET',
                url: urls.BASE + urlRest,
                params:{content:data}
            }).success( sucess ).error( error);            
        },
        actualizar: function( urlRest,data,sucess,error){
            
            $http({
                method: 'PUT',
                url: urls.BASE + urlRest,
                headers: {'Content-Type': 'text/plain'},
                data: data
            }).success( sucess ).error( error);            
        },
        eliminar: function( urlRest,data,sucess,error){
            
            $http({
                method: 'DELETE',
                url: urls.BASE + urlRest,
                params:{content:data}
            }).success( sucess ).error( error);            
        },
        subirArchivo: function(urlRest,file,sucess,error){
            
            var formData = new FormData();
            formData.append("file", file,file.name);
            
            $http({
                method: 'POST',
                url: urls.BASE + urlRest,
                headers: {'Content-Type': undefined,transformRequest: angular.identity},
                data: formData
            }).success( sucess ).error( error);
            
        },
        verificarToken: function(){
            
            $http({
                method: 'HEAD',
                url: urls.BASE +"/login"
            }).success( function(){
                console.log("BIENVENIDO AL SISTEMA");
            }).error( function(data, status, headers, config){
                console.log("ERROR "+status);
            });
        },
        crearRequest: function(identity,scope){
            return new Request(identity,scope);
        }
        
    };
}]);

app.factory('modal', ['$compile','$interpolate', function($compile,$interpolate){
    return{
        mensaje: function(titulo,mensaje){
            $.gritter.add({
                // (string | mandatory) the heading of the notification
                title: titulo,
                // (string | mandatory) the text inside the notification
                text: mensaje,
                // (string | optional) the image to display on the left
                //image: 'assets/img/ui-sam.jpg',
                // (bool | optional) if you want it to fade out on its own or just sit there
                sticky: false,
                // (int | optional) the time you want it to be alive for before fading out
                time: '2000',
                // (string | optional) the class name you want to apply to that specific message
                class_name: 'my-sticky-class'
            });
        },
        mensajeConfirmacion: function($scope,mensaje,funcion){
            
            $scope.miFuncionConfirmacion = funcion;
            $compile('<confirmacion mensaje="'+mensaje+'" accion="miFuncionConfirmacion"></confirmacion>')($scope);
            
        }
    };
}]);     

/* Claves del REQUEST*/
KEY_REQUEST_STR = "i.type";
REQUEST_STR = "inet-req";
REQUEST_ID_STR = "cmd";
REQUEST_IDENTITY_STR = "identity";
REQUEST_SCOPE_STR = "scope";
REQUEST_META_STR = "meta";
REQUEST_DATA_STR = "data";

/* Objeto Request */
function Request(identity,scope){

    this[KEY_REQUEST_STR] = REQUEST_STR;
    this[REQUEST_SCOPE_STR] = scope;
    this[REQUEST_IDENTITY_STR] = identity;
    this[REQUEST_META_STR] = new Object();

    this.getCmd = function(){
        return this.mCurrendCommand;
    };
    this.setCmd = function(dominio,version,accion){
        this[REQUEST_ID_STR] = dominio+"@"+version+":"+accion;
    };
    this.getIdentity = function(){
        return this.mIdentity;
    };
    this.setIdentity = function(identity){
        this[REQUEST_IDENTITY_STR] = identity;
    };
    this.getScope = function(){
        return this.mScope;
    };
    this.setScope = function(scope){
        this[REQUEST_SCOPE_STR] = scope;
    };
    this.getData = function(){
        return this.mData;
    };
    this.setData = function(data){
        this[REQUEST_DATA_STR] = data;
    };
    this.getMetadata = function(){
        return this[REQUEST_META_STR];
    };
    this.setMetadataValue = function(key,value){
        if(!(value instanceof Array))
            this[REQUEST_META_STR][key] = [value];
    };    
    this.setMetadataValues = function(key,values){
        if(values instanceof Array)
            this[REQUEST_META_STR][key] = values;
    };
};


/* CHAT*/
function Chat(url){
    this.wsocket;
    this.conectar = function(funMensaje){
        if ("WebSocket" in window){
            this.wsocket = new WebSocket(url);
            this.wsocket.onmessage = function (evt) {
                funMensaje(evt.data);              
            };
            this.wsocket.onopen = function(){
              // Web Socket is connected
              console.log('conexion establecida al chat');
            };
            this.wsocket.onclose = function(){
               // websocket is closed.
               if(this.wsocket.readyState==3){
                   alert("LA APLICACION NO DETECTO EL CHAT");
                   return;
               }
               this.wsocket = null;
            };
        }
        else
            console.log("Chat no soportado!");
    };
    this.enviarMensaje = function (mensaje){
        if(this.wsocket.readyState!=1){
            alert("no se puede enviar el mensaje, coneccion fallida");
            return;
        }
        this.wsocket.send(mensaje);
    };    
};

/*Funcion que busca dentro de un array*/
function buscarContenido(lista, labelClave, labelContenido, idBuscado){
    for(var i=0;i<lista.length;i++ ){
        if(lista[i][labelClave] === idBuscado)
            return lista[i][labelContenido];
    }
};
/*objeto que contiene informacion acerca de nuestro archivo*/
function MyFile(nombre){
    this.name = nombre; //nombre del archivo con su extension
    this.type;          //tipo de archivo (ej application, image etc)    
    this.codeType;      //tipo codificacion del contenido (ej base64, base32, etc)
    this.data;          //contenido del archivo
        
    //trabajamos sobre el dataURL --> "data:application/pdf;base64,datossss"  "data:image/png;base64,datosss"
    this.parseDataURL = function(dataURL){
        var dataURLSeparado = dataURL.split(",");
        
        //obtenemos los datos
        this.data = dataURLSeparado[1];
        //la primera parte es la cabecera del archivo
        var cabecera = dataURLSeparado[0].split(";");       
        
        //obtenemos el typo de codificacion
        this.codeType = cabecera[1];
        //obtenemos el tipo de archivo
        this.type = cabecera[0].split(":")[1];
    };
};
