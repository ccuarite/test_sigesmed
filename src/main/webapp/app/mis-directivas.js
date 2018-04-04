'use strict';

app.directive('dialogoChat',[ function () {
return {
    restrict: 'E',
    scope: {
        usuario:'@',
        imagen:'@',
        mensaje:'@',
        clase:'@'
    },
    template:
            '<li class="clearfix {{clase}}">'+
                '<div class="chat-avatar">'+
                    '<img ng-src="{{imagen}}" alt="female" style="width: 29px;height: 29px;">'+
                    '<i>10:00</i>'+
                '</div>'+
                '<div class="conversation-text">'+
                    '<div class="ctext-wrap">'+
                        '<i>{{usuario}}</i>'+
                        '<p>{{mensaje}}</p>'+
                    '</div>'+
                '</div>'+
            '</li>'            
    };
}]);

app.directive('chat',['$compile','urls', function ($compile,urls) {
return {
    replace: true,
    restrict: 'E',
    scope: {
    },
    template:
            '<div class="chat">'+
                '<header class="chat-head" >Chat Sigesmed</header>'+
                '<div class="chat-conversacion">'+
                    '<ul id="conversacion" class="conversation-list">'+
                    '</ul>'+
                    '<input type="text" class="form-control" placeholder="ingrese texto" />'+
                '</div>'+
            '</div>'
    ,
    link: function(scope,elem,attrs){
        
        var conversacion = elem.find('#conversacion');
        var inputChat = elem.find('input');
        
        scope.chat = new Chat("ws://"+document.location.host+urls.BASECHAT);
        
        scope.chat.conectar(function(data){
            var nuevoDialogo = $compile('<dialogo-chat usuario="otro" mensaje="'+data+'" imagen="../recursos/img/avatar1_small.jpg" ></dialogo-chat>')(scope);                
            conversacion.append(nuevoDialogo);
        });
        
        inputChat.bind('keypress',function(e){
            if (e.which == 13  ) {                
                scope.chat.enviarMensaje(inputChat.val());                
                var nuevoDialogo = $compile('<dialogo-chat usuario="yo" mensaje="'+inputChat.val()+'" imagen="../recursos/img/avatar2.png" clase="odd"/></dialogo-chat>')(scope);
                conversacion.append(nuevoDialogo);
                inputChat.val("");
            }
        });
        
        
    }
  };
}]);


app.directive('confirmacion',[ function () {
return {
    replace: true,
    restrict: 'E',
    scope: {
        mensaje:'@',
        accion: '='
    },
    template:
            '<div class="modal fade" id="modalConfirmacion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
                '<div class="modal-dialog dlg-confirmacion">'+
                  '<div class="modal-content">'+
                    '<div class="modal-header">'+
                      '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
                      '<h4 class="modal-title" id="myModalLabel">DIALOGO DE CONFIRMACION</h4>'+
                    '</div>'+
                    '<div class="modal-body">{{mensaje}}</div>'+
                    '<div class="modal-footer">'+
                      '<button type="button" class="btn btn-default" ng-click="funcionNo()"><i class="fa fa-times" ></i> No</button>'+
                      '<button type="button" class="btn btn-warning" ng-click="funcionSi()"><i class="fa fa-check" ></i> Si</button>'+
                    '</div>'+
                  '</div>'+
                '</div>'+
              '</div>'
      ,
      link: function(scope,elem,attrs){
          scope.funcionSi = function(){
              scope.accion();
              $('#modalConfirmacion').modal('hide');
              setTimeout(function(){ $('#modalConfirmacion').remove(); }, 1000);
              
              delete scope.accion;
          };
          scope.funcionNo = function(){
              $('#modalConfirmacion').modal('hide');
              setTimeout(function(){ $('#modalConfirmacion').remove(); }, 1000);
              //;
          }
        $("#main-content").append(elem);
        $('#modalConfirmacion').modal('show');      
      }
    };
}]);


app.directive('noticias',[ function () {
return {
    replace: true,
    restrict: 'E',
    scope: {
        noticias: '='
    },
    template:
            '<div class="noticias">'+
            '<ul class="right-side-accordion">'+
            '<li class="widget-collapsible">'+
                '<a href class="head widget-head purple-bg active" ng-click="verNoticias()">'+
                    '<span class="pull-left"> Noticias ({{noticias.length}})</span>'+
                    '<span class="pull-right widget-collapse"><i class="ico-minus"></i></span>'+
                '</a>'+
                '<ul id="mis-noticias" class="widget-container">'+
                    '<li>'+
                        '<div class="prog-row" ng-repeat="n in noticias">'+
                            '<div class="user-thumb rsn-activity">'+
                                '<i class="fa fa-clock-o"></i>'+
                            '</div>'+
                            '<div class="rsn-details ">'+
                                '<p class="text-muted">{{n.hora}}</p>'+
                                '<p><a >{{n.titulo}}</a>{{n.descripcion}}</p>'+
                            '</div>'+
                        '</div>'+
                    '</li>'+
                '</ul>'+
            '</li>'+
            '</ul>'+
            '</div>',
    link: function(scope,elem,attrs){
          scope.verNoticias = function(){
              $('#mis-noticias').slideToggle('slow');
          };
        $('.widget-container').css({"display":"none"});     
      }
    };
}]);


app.directive('notificaciones',[ function () {
return {
    replace: true,
    restrict: 'E',
    scope: {
        notificaciones: '='
    },
    template:
            '<li id="header_notification_bar" class="dropdown">'+
                '<a data-toggle="dropdown" class="dropdown-toggle" href>'+
                    '<i class="fa fa-bell-o"></i>'+
                    '<span class="badge bg-warning">{{notificaciones.length}}</span>'+
                '</a>'+
                '<ul class="dropdown-menu extended notification">'+
                    '<li>'+
                        '<p>Notificaciones</p>'+
                    '</li>'+
                    '<li ng-repeat="n in notificaciones">'+
                        '<div class="alert clearfix {{n.tipo}}" >'+
                            '<span class="alert-icon"><i class="fa fa-bolt"></i></span>'+
                            '<div class="noti-info">'+
                                '<a href="#"> {{n.titulo}}</a>'+
                            '</div>'+
                        '</div>'+
                    '</li>'+
                '</ul>'+
            '</li>',
    link: function(scope,elem,attrs){
           
      }
    };
}]);

app.directive('mensajes',[ function () {
return {
    replace: true,
    restrict: 'E',
    scope: {
        mensajes: '='
    },
    template:
            '<li id="header_inbox_bar" class="dropdown" style="margin-left:0">'+
                '<a data-toggle="dropdown" class="dropdown-toggle" href>'+
                    '<i class="fa fa-envelope-o"></i>'+
                    '<span class="badge bg-important">{{mensajes.length}}</span>'+
                '</a>'+
                '<ul class="dropdown-menu extended inbox">'+
                    '<li>'+
                        '<p class="red">Tienes {{m.mensajes}} Mensajes</p>'+
                    '</li>'+
                    '<li ng-repeat="m in mensajes">'+
                        '<a href >'+
                            '<span class="photo"><img alt="avatar" src="../recursos/img/avatar1_small.jpg"></span>'+
                                '<span class="subject">'+
                                '<span class="from">{{m.nombre}}</span>'+
                                '<span class="time">{{m.hora}}</span>'+
                                '</span>'+
                                '<span class="message">{{m.contenido}}</span>'+
                        '</a>'+
                    '</li>'+
                '</ul>'+
            '</li>',
    link: function(scope,elem,attrs){
           
      }
    };
}]);


/*Componente para subir un archivo*/
app.directive('inputFile',[ function () {
return {
    restrict: 'E',
    scope: {
        archivo: '='
    },
    template:
            '<input type="file" class="mi-input" onchange="angular.element(this).scope().cambiarMiArchivo(this)"></input><span>{{archivo.name}}</span>'
    ,
    link: function(scope,elem,attrs){
          scope.cambiarMiArchivo = function(input){
            scope.$apply(function () {
                scope.archivo = crearMyFile(input.files[0]);
            });
              
              /*input.files[0] = {};//reiniciando el archivo*/
          };
      }
    };
}]);

/*funciones para crear un objeto tipo archivo*/
/*funcion que lee un input file para crear un objecto MyFile*/
function crearMyFile(file){    
    var miArchivo = new MyFile(file.name);

    var reader = new FileReader();
    //implementando la funcion onload
    reader.onload = function(){
        miArchivo.parseDataURL(reader.result );
    };

    //leendo la imagen
    reader.readAsDataURL(file);
    
    return miArchivo;
};
//varios archivos
function crearArrayMyFile(files){
    
    var listaArchivos = [];

    // Loop through the FileList and render image files as thumbnails.
    for (var i = 0, f; f = files[i]; i++) {
        
        var file = f; //un solo archivo
        var miArchivo = new MyFile(file.name);

        var reader = new FileReader();
        //implementando la funcion onload
        reader.onload = function(){
            miArchivo.parseDataURL(reader.result );
        };

        //leendo la imagen
        reader.readAsDataURL(file);

        listaArchivos.push( miArchivo );
      
    }
    return listaArchivos;
    
};







