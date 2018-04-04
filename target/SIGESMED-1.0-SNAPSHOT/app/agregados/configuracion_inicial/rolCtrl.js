app.controller("rolCtrl",["$scope","crud","modal", function ($scope,crud,modal){
        
    $scope.roles = [];
    $scope.subModulos= [];
    $scope.subModulosSel= [];
    
    $scope.subModulosEdi= [];
    $scope.subModulosSelEdi= [];
    $scope.nuevoRol = {abreviatura:"",nombre:"",descripcion:"",estado:'A'};
    $scope.rolSel = {};
    
    
    $scope.listarRoles = function(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('rol',1,'listarRolesConFunciones');
        crud.listar("/configuracionInicial",request,function(data){
            $scope.roles = data.data;
        },function(data){
            console.info(data);
        });
    };
    $scope.agregarRol = function(nomUsu){
        
        if($scope.subModulosSel.length ===0){
            modal.mensaje("ALERTA","No hay funciones asignadas al rol");
            return;
        }
        $scope.nuevoRol.funciones = [];
        
        for(var i=0;i<$scope.subModulosSel.length;i++ ){
            fun = $scope.subModulosSel[i].funciones;            
            for(var j=0;fun && j<fun.length;j++ ){
                $scope.nuevoRol.funciones.push( fun[j] );
            }  
        }
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('rol',1,'insertarRol');
        request.setData($scope.nuevoRol);
        
        crud.insertar("/configuracionInicial",request,function(response){
            
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){
                //recuperamos las variables que nos envio el servidor
                $scope.nuevoRol.rolID = response.data.rolID;
                $scope.nuevoRol.fecha = response.data.fecha;
                
                //insertamos el elemento a la lista
                $scope.roles.push($scope.nuevoRol);
                //reiniciamos las variables
                $scope.nuevoRol = {abreviatura:"",nombre:"",descripcion:"",estado:'A'};
                $scope.subModulosSel= [];
                $scope.subModulos = JSON.parse(JSON.stringify($scope.subModulosBase));
                //cerramos la ventana modal
                $('#modalNuevo').modal('hide');
            }            
        },function(data){
            console.info(data);
        });
        
    };
    $scope.eliminarRol = function(i,idDato,nomUsu){
        
        modal.mensajeConfirmacion($scope,"seguro que desea eliminar el Rol",function(){
            
            var request = crud.crearRequest(nomUsu,"web");
            request.setCmd('rol',1,'eliminarRol');
            request.setData({rolID:idDato});

            crud.eliminar("/configuracionInicial",request,function(response){

                modal.mensaje("CONFIRMACION",response.responseMsg);
                if(response.responseSta)
                    $scope.roles.splice(i,1);

            },function(data){
                console.info(data);
            });
            
        });
        
        
        
    };
    $scope.prepararEditar = function(i,t){
        $scope.rolSel = JSON.parse(JSON.stringify(t));
        $scope.rolSel.i = i;
        
        $scope.subModulosEdi= [];
        $scope.subModulosSelEdi= [];
        $scope.subModulosEdi = JSON.parse(JSON.stringify($scope.subModulosBase));
        
        
        for(var i=0;i<$scope.subModulosEdi.length;i++ ){
            var subMod = $scope.subModulosEdi[i];
            for(var j=0;j<subMod.funciones.length;j++ ){
                
                var fun = subMod.funciones[j];
                
                for(var k=0;k<$scope.rolSel.funciones.length;k++ ){
                    if($scope.rolSel.funciones[k].funcionID===fun.funcionID){

                        

                        if( subMod.sel >= 0){
                            var subModSel = $scope.subModulosSelEdi[subMod.sel];            
                            subModSel.funciones.push(fun);
                            fun.ver = true;
                            if(subMod.funciones.length===subModSel.funciones.length)
                                subMod.ver = true;
                        }
                        else{
                            subMod.sel = $scope.subModulosSelEdi.length;
                            var nuevoSubMod = {codigo:subMod.codigo,nombre:subMod.nombre,subModuloID:subMod.subModuloID,sel:i,funciones:[]};

                            nuevoSubMod.funciones.push(fun);
                            fun.ver = true;
                            $scope.subModulosSelEdi.push(nuevoSubMod);

                            if(subMod.funciones.length===nuevoSubMod.funciones.length)
                                subMod.ver = true;
                        }
                    }
                }
            }
        }        
        
        $('#modalEditar').modal('show');
    };
    $scope.editarRol = function(nomUsu){
        
        if($scope.subModulosSelEdi.length ===0){
            modal.mensaje("ALERTA","No hay funciones asignadas al rol");
            return;
        }
        
        $scope.rolSel.funciones = [];
        
        for(var i=0;i<$scope.subModulosSelEdi.length;i++ ){
            fun = $scope.subModulosSelEdi[i].funciones;            
            for(var j=0;fun && j<fun.length;j++ ){
                $scope.rolSel.funciones.push( fun[j] );
            }  
        }
        
        var request = crud.crearRequest(nomUsu,"web");
        request.setCmd('rol',1,'actualizarRol');
        request.setData($scope.rolSel);
                
        crud.actualizar("/configuracionInicial",request,function(response){
            modal.mensaje("CONFIRMACION",response.responseMsg);
            if(response.responseSta){                
                //actualizando
                $scope.roles[$scope.rolSel.i] = $scope.rolSel;
                $('#modalEditar').modal('hide');
            }
        },function(data){
            console.info(data);
        });
    };
    $scope.eliminarModulos = function(){
            
        
    };
    
    $scope.seleccionarFuncion = function(i,j){
        var subMod = $scope.subModulos[i];
        var fun = subMod.funciones[j];
        
        if( subMod.sel >= 0){
            var subModSel = $scope.subModulosSel[subMod.sel];            
            subModSel.funciones.push(fun);
            fun.ver = true;
            if(subMod.funciones.length===subModSel.funciones.length)
                subMod.ver = true;
        }
        else{
            subMod.sel = $scope.subModulosSel.length;
            var nuevoSubMod = {codigo:subMod.codigo,nombre:subMod.nombre,subModuloID:subMod.subModuloID,sel:i,funciones:[]};
            
            nuevoSubMod.funciones.push(fun);
            fun.ver = true;
            $scope.subModulosSel.push(nuevoSubMod);
            
            if(subMod.funciones.length===nuevoSubMod.funciones.length)
                subMod.ver = true;
        }
    };    
    $scope.desSeleccionarFuncion = function(i,j){
        
        var subMod = $scope.subModulosSel[i];
        var fun = subMod.funciones[j];
        
        fun.ver = false;
        subMod.funciones.splice(j,1);
        
        if(subMod.funciones.length < $scope.subModulos[subMod.sel].funciones.length){
            $scope.subModulos[subMod.sel].ver = false;            
        }
        if(subMod.funciones.length===0){
            $scope.subModulosSel.splice(i,1);
            delete $scope.subModulos[subMod.sel].sel;
        }
    };
    
    $scope.seleccionarFuncion2 = function(i,j){
        var subMod = $scope.subModulosEdi[i];
        var fun = subMod.funciones[j];
        
        if( subMod.sel >= 0){
            var subModSel = $scope.subModulosSelEdi[subMod.sel];            
            subModSel.funciones.push(fun);
            fun.ver = true;
            if(subMod.funciones.length===subModSel.funciones.length)
                subMod.ver = true;
        }
        else{
            subMod.sel = $scope.subModulosSelEdi.length;
            var nuevoSubMod = {codigo:subMod.codigo,nombre:subMod.nombre,subModuloID:subMod.subModuloID,sel:i,funciones:[]};
            
            nuevoSubMod.funciones.push(fun);
            fun.ver = true;
            $scope.subModulosSelEdi.push(nuevoSubMod);
            
            if(subMod.funciones.length===nuevoSubMod.funciones.length)
                subMod.ver = true;
        }
    };    
    $scope.desSeleccionarFuncion2 = function(i,j){
        
        var subMod = $scope.subModulosSelEdi[i];
        var fun = subMod.funciones[j];
        
        fun.ver = false;
        subMod.funciones.splice(j,1);
        
        if(subMod.funciones.length < $scope.subModulosEdi[subMod.sel].funciones.length){
            $scope.subModulosEdi[subMod.sel].ver = false;            
        }
        if(subMod.funciones.length===0){
            $scope.subModulosSelEdi.splice(i,1);
            delete $scope.subModulosEdi[subMod.sel].sel;
        }
    };
    
    listarSubModulos();
    
    function listarSubModulos(){
        //preparamos un objeto request
        var request = crud.crearRequest("admin","web");
        //asignamos la operacion a realizar
        request.setCmd('subModuloSistema',1,'listarSubModulosConFunciones');
        //llamamos al servicio listar, le damos la ruta donde se encuentra el recurso,el objeto request
        //y las roles de exito y error
        crud.listar("/configuracionInicial",request,function(data){
            $scope.subModulosBase = data.data;
            $scope.subModulos = JSON.parse(JSON.stringify($scope.subModulosBase));
            
            
        },function(data){
            console.info(data);
        });
    };
    
    
    
    
}]);