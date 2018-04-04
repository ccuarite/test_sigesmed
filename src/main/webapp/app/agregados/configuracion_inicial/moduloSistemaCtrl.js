app.controller("moduloSistemaCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.modulos = [];
    $scope.nuevoModulo = {codigo:"",nombre:"",descripcion:"",icono:"",estado:'A'};
    $scope.moduloSel = {};
    
    
    $scope.listarModulos = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('moduloSistema',1,'listarModulos');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las funciones de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.modulos = data.data;
        },function(data){
            console.info(data);
        });
    };    
    $scope.agregarModulo = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('moduloSistema',1,'insertarModulo');
        request.setData($scope.nuevoModulo);
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevoModulo.moduloID = response.data.moduloID;
                $scope.nuevoModulo.fecha = response.data.fecha;
                
                //insertamos el elemento a la lista
                $scope.modulos.push($scope.nuevoModulo);                
                //reiniciamos las variables
                $scope.nuevoModulo = {codigo:"",nombre:"",descripcion:"",icono:"",estado:'A'};
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarModulo = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar este registro",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('moduloSistema',1,'eliminarModulo');
            request.setData({moduloID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.modulos.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.moduloSel = JSON.parse(JSON.stringify(t));
        $scope.moduloSel.i = i;
        $('#modalEditar').modal('show');
    };
    $scope.editarModulo = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('moduloSistema',1,'actualizarModulo');
        request.setData($scope.moduloSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.modulos[$scope.moduloSel.i] = $scope.moduloSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    $scope.eliminarModulos = function(){
            
        
    };
    
    
}]);