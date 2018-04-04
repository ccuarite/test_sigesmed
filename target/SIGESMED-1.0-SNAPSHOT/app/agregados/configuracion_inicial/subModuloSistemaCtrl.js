app.controller("subModuloSistemaCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.subModulos = [];
    $scope.modulos = [];
    $scope.nuevoSubModulo = {codigo:"",nombre:"",descripcion:"",icono:"",estado:'A',moduloID:0};
    $scope.subModuloSel = {};
    
    
    $scope.listarSubModulos = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('subModuloSistema',1,'listarSubModulos');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las funciones de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.subModulos = data.data;
        },function(data){
            console.info(data);
        });
    };
    $scope.agregarSubModulo = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('subModuloSistema',1,'insertarSubModulo');
        request.setData($scope.nuevoSubModulo);
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevoSubModulo.subModuloID = response.data.subModuloID;
                $scope.nuevoSubModulo.fecha = response.data.fecha;
                
                //insertamos el elemento a la lista
                $scope.subModulos.push($scope.nuevoSubModulo);
                //reiniciamos las variables
                $scope.nuevoSubModulo = {codigo:"",nombre:"",descripcion:"",icono:"",estado:'A',moduloID:0};
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarSubModulo = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar este registro",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('subModuloSistema',1,'eliminarSubModulo');
            request.setData({subModuloID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.subModulos.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.subModuloSel = JSON.parse(JSON.stringify(t));
        $scope.subModuloSel.i = i;
        $('#modalEditar').modal('show');
    };
    $scope.editarSubModulo = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('subModuloSistema',1,'actualizarSubModulo');
        request.setData($scope.subModuloSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.subModulos[$scope.subModuloSel.i] = $scope.subModuloSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    $scope.eliminarModulos = function(){
            
        
    };
    
    listarModulos();
    
    function listarModulos(){
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
    
    
}]);