app.controller("usuarioSistemaCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.usuarios = [];
    $scope.roles = [];
    $scope.organizaciones = [];
    $scope.nuevoUsuario = {nombre:"",password:"",estado:'A',rolID:0,organizacionID:0};
    $scope.usuarioSel = {};
    
    
    $scope.listarUsuarios = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('usuarioSistema',1,'listarUsuarios');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las usuarios de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.usuarios = data.data;
        },function(data){
            console.info(data);
        });
    };
    $scope.agregarUsuario = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('usuarioSistema',1,'insertarUsuario');
        request.setData($scope.nuevoUsuario);      
        
        
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevoUsuario.usuarioID = response.data.usuarioID;                
                $scope.nuevoUsuario.rol = buscarContenido($scope.roles,"rolID","nombre",$scope.nuevoUsuario.rolID);
                $scope.nuevoUsuario.organizacion = buscarContenido($scope.organizaciones,"organizacionID","nombre",$scope.nuevoUsuario.organizacionID);
                
                //insertamos el elemento a la lista                
                $scope.usuarios.push($scope.nuevoUsuario);
                //reiniciamos las variables
                $scope.nuevoUsuario = {nombre:"",password:"",estado:'A',rolID:0,organizacionID:0};
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarUsuario = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar este registro",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('usuarioSistema',1,'eliminarUsuario');
            request.setData({usuarioID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.usuarios.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.usuarioSel = JSON.parse(JSON.stringify(t));
        $scope.usuarioSel.i = i;
        $('#modalEditar').modal('show');
    };
    $scope.editarUsuario = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('usuarioSistema',1,'actualizarUsuario');
        request.setData($scope.usuarioSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.usuarioSel.rol = buscarContenido($scope.roles,"rolID","nombre",$scope.usuarioSel.rolID);
                $scope.usuarioSel.organizacion = buscarContenido($scope.organizaciones,"organizacionID","nombre",$scope.usuarioSel.organizacionID);
                $scope.usuarios[$scope.usuarioSel.i] = $scope.usuarioSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    
    listarRoles();
    listarOrganizaciones();
    
    function listarRoles(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('rol',1,'listarRoles');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las usuarios de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.roles = data.data;
        },function(data){
            console.info(data);
        });
    };
    function listarOrganizaciones(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('organizacion',1,'listarOrganizaciones');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las usuarios de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.organizaciones = data.data;
        },function(data){
            console.info(data);
        });
    }; 
    
    
}]);
