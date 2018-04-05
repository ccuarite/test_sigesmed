//creamos nuestro modulo llamado appLogin para logearnos
var app = angular.module("appLogin", ['ngRoute']);

app.constant('urls', {
    BASE: '/rest',
    BASECONTEXTO: '/'
});
app.config(['$routeProvider','$httpProvider',function($routeProvider,$httpProvider) {
        
    //reiniciando las variables
    localStorage.clear();
    
    //interceptamos las peticiones
    $httpProvider.interceptors.push(['$q','$location',function($q,$location){
        return{
            'request': function(config){
                config.headers = config.headers || {};
                if(localStorage.getItem('jwt')){
                    config.headers.autorizacion = localStorage.getItem('jwt');
                }
                return config;
            },
            'responseError': function(response){
                if(response.status === 401 || response.status === 403 || response.status === 400){                   
                   localStorage.clear();
                   $location.path('/');
                }
                return $q.reject(response);
            }
        };
    }]);
    //hacemos el ruteo de nuestra aplicación
    $routeProvider.when("/", {
            templateUrl : "login.html",
            controller : "loginCtrl"
    })
    .otherwise({ redirectTo : "/"});
}]);
app.factory('servicioLogin', ['$http','urls', function($http,urls){
    return{
        buscarUsuario: function(data,sucess,error){
            $http({
                method: 'POST',
                url: urls.BASE + "/login",
                headers: {'Content-Type': 'text/plain'},
                data: data
            }).success( sucess ).error( error);
        },
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
                time: '2500',
                // (string | optional) the class name you want to apply to that specific message
                class_name: 'my-sticky-class'
            });
            
        }
    };
}]);
app.controller('loginCtrl', ['$scope','servicioLogin','urls', function($scope,servicioLogin,urls){
     
     $scope.usuario={nombre:"",password:""};

    function succesIniciarSession(response){
        
        if( response.responseSta ){
            var objResponse = response.data;
            localStorage.setItem('jwt', objResponse.jwt);
            localStorage.setItem('nombre', objResponse.nombre);
            localStorage.setItem('rol', objResponse.rol);
            localStorage.setItem('organizacion', JSON.stringify(objResponse.organizacion));
            localStorage.setItem('modulos', JSON.stringify(objResponse.modulos));
            location.replace( urls.BASECONTEXTO + objResponse.url+"#menuInicio" );
            return;
        }
        servicioLogin.mensaje("MENSAJE",response.responseMsg);
    };
    function errorIniciarSession(response){
        console.log(response);
    };
    $scope.iniciarSession = function(){

        var request = new Request($scope.usuario.nombre,'web');
        request.setCmd('login',1,'signin');
        //request.setMetadataValue('user.password','sh5');
        request.setData($scope.usuario);
        servicioLogin.buscarUsuario(request,succesIniciarSession,errorIniciarSession);
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