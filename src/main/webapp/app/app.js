//creamos nuestro modulo llamado app
var app = angular.module("app", ['ngRoute']);

app.constant('urls', {
    BASE: '/SIGESMED/rest',
    BASELOGIN: '/SIGESMED/login.html',
    BASECHAT: "/SIGESMED/chat_sigesmed"
});

app.config(['$httpProvider','$routeProvider',function($httpProvider,$routeProvider) {
    
    $httpProvider.interceptors.push(['$q','urls',function($q,urls){
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
                   location.replace(urls.BASELOGIN );
                   return;
                }
                return $q.reject(response);
            }
        };
    }]);
    //hacemos el ruteo de las interfaces de nuestra aplicación
    $routeProvider.when("/menuInicio", {
        templateUrl : "menuInicio.html"
    })
    .otherwise({ redirectTo : "/"});
    
    var modulos = localStorage.getItem('modulos');
    if(modulos){
        //cargamos el ruteo        
        cargaRuteoApp($routeProvider,JSON.parse(modulos));
    }

}]);

//metodo run se inicia una sola vez
app.run(['$rootScope','$location','urls','crud',function($rootScope,$location, urls,crud) {
        
    $rootScope.usuMaster = {nombre:"",rol:"",organizacion:""};
    $rootScope.menuPrincipal = [];
    
    $rootScope.noticias = [{hora:"0:0",titulo:"noticia nueva",descripcion:"descripcion"},{hora:"0:0",titulo:"noticia nueva",descripcion:"descripcion"}];
    $rootScope.notificaciones = [{tipo:"alert-info",titulo:"notificacino de alerta"},{tipo:"alert-danger",titulo:"notificacino de peligro"},{tipo:"alert-success",titulo:"notificacino de exito"}];
    $rootScope.mensajes = [{nombre:"profesor",hora:"10:00",contenido:"hola como estas"},{nombre:"profesor",hora:"10:00",contenido:"hola como estas"}];
    
    
    $rootScope.menu = [];
    $rootScope.modNom = "";
    $rootScope.subModNom = "";
    $rootScope.visNom = "";
    //configuracion inicial "no tocar"
    if(!localStorage.getItem('jwt') || !localStorage.getItem('modulos')){
        localStorage.clear();
        location.replace(urls.BASELOGIN );
    }
    else{
        crud.verificarToken();
        $rootScope.usuMaster.nombre = localStorage.getItem('nombre');
        $rootScope.usuMaster.rol = localStorage.getItem('rol');
        $rootScope.usuMaster.organizacion = JSON.parse( localStorage.getItem('organizacion') );
        
        $rootScope.menuPrincipal = JSON.parse( localStorage.getItem('modulos') );
        location.replace("#menuInicio" );
        
    }
    $rootScope.cerrarSession = function (){
        localStorage.clear();
        location.replace(urls.BASELOGIN );
    };
    
    $rootScope.inicio = function(){
        $rootScope.menu = [];
        $rootScope.modNom = "";
        $rootScope.subModNom = "";
        $rootScope.visNom = "";
        $location.path("menuInicio");
    };
        
    $rootScope.elegirMenu = function(menu){        
        $rootScope.menu = menu.subModulos;
        $rootScope.modNom = menu.nombre;
        $rootScope.subModNom = "";
        $rootScope.visNom = "";
        $location.path("#");
    };
    $rootScope.menuToggle = function(e,subModulo){
        if(subModulo.funciones){
            var menuAnterior = $(".leftside-navigation .sub-menu.activo");
            var menu;
            if( e.target.parentElement.tagName =='LI' )
                menu = $(e.target.parentElement);
            else
                menu = $(e.target.parentElement.parentElement);

            if(!menu.hasClass('activo')){
                $rootScope.subModNom = subModulo.nombre;
                menu.find('.sub').fadeToggle( 600, "swing" );
                menu.addClass('activo');
            }else{
                $rootScope.subModNom = "";
                $rootScope.visNom = "";
                $location.path("#");
                
            }
            menuAnterior.find('.sub').fadeToggle( 500, "swing" );
            menuAnterior.removeClass('activo');
        }
    };
    $rootScope.elegirVista = function(vista){
        $rootScope.visNom = vista.nombre;
    };
    //comprimiendo los menus
    //$('.sub-menu .sub').css({"display":"none"});
    //Fin
    /*
    $rootScope.menu = $rootScope.menuPrincipal[0].subModulos;
    $rootScope.modNom = $rootScope.menuPrincipal[0].nombre;*/
    
    
    //funcion que se llama antes de cambiar la ruta (link)
    /*
    $rootScope.$on('$routeChangeStart', function(event, next, current) {
        alert("next: "+next.templateUrl+"   current: "+current);
        
        if ( localStorage.getItem('jwt') == null ) {
            $window.location.href = $window.location.href + "/";
        }
        else {
            //var usuario = localStorage.getItem('usuario');

            if( next.templateUrl == 'app/login.html' )
                $window.location.href = $window.location.href + "/";
                //(next.templateUrl == 'views/inicio.html' || usuario.puesto != 1) {
                    //$location.path('/tareas');
                //}
        }
    });*/
    
}]);
//cargando los archivos javascript
var modulos = localStorage.getItem('modulos');
if(modulos)
    cargaArchivosJavascript( JSON.parse(modulos) );

function cargaArchivosJavascript(modulos){
    
    var subModulos;
    var funciones;

    for(var i=0;i<modulos.length;i++ ){
        subModulos = modulos[i].subModulos;
        for(var j=0;subModulos && j<subModulos.length;j++ ){
            funciones = subModulos[j].funciones;
            for(var k=0;funciones && k<funciones.length;k++ ){
                console.log(funciones[k].url+funciones[k].controlador);
                document.write('<script src="'+funciones[k].url+funciones[k].controlador+'.js"></script>');
            }
        }  
    }    
};
function cargaRuteoApp(router,modulos){
    
    var subModulos;
    var funciones;

    for(var i=0;i<modulos.length;i++ ){
        subModulos = modulos[i].subModulos;
        for(var j=0;subModulos && j<subModulos.length;j++ ){
            funciones = subModulos[j].funciones;
            for(var k=0;funciones && k<funciones.length;k++ ){
                router.when("/"+funciones[k].clave, {
                    templateUrl : funciones[k].url+funciones[k].interfaz,
                    controller : funciones[k].controlador
                });
            }
        }
    }  
};
