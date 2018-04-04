app.controller("funcionSistemaCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.funciones = [];
    $scope.subModulos = [];
    $scope.nuevaFuncion = {nombre:"",descripcion:"",url:"",clave:"",controlador:"",interfaz:"",icono:"",estado:'A',subModuloID:0};
    $scope.funcionSel = {};
    
    
    $scope.listarFunciones = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('funcionSistema',1,'listarFunciones');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las funciones de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.funciones = data.data;
        },function(data){
            console.info(data);
        });
    };
    $scope.agregarFuncion = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('funcionSistema',1,'insertarFuncion');
        request.setData($scope.nuevaFuncion);
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevaFuncion.funcionID = response.data.funcionID;
                $scope.nuevaFuncion.fecha = response.data.fecha;
                
                //insertamos el elemento a la lista
                $scope.funciones.push($scope.nuevaFuncion);
                //reiniciamos las variables
                $scope.nuevaFuncion = {nombre:"",descripcion:"",url:"",clave:"",controlador:"",interfaz:"",icono:"",estado:'A',subModuloID:0};
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarFuncion = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar este registro",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('funcionSistema',1,'eliminarFuncion');
            request.setData({funcionID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.funciones.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.funcionSel = JSON.parse(JSON.stringify(t));
        $scope.funcionSel.i = i;
        $('#modalEditar').modal('show');
    };
    $scope.editarFuncion = function(nomUsu){
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('funcionSistema',1,'actualizarFuncion');
        request.setData($scope.funcionSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.funciones[$scope.funcionSel.i] = $scope.funcionSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    $scope.eliminarModulos = function(){
            
        
    };
    
    listarSubModulos();
    
    function listarSubModulos(){
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
    
    
}]);