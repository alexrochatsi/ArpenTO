
function show5(){
    var hoje = new Date()
    var hours = hoje.getHours()
    var minutes = hoje.getMinutes()
    var seconds = hoje.getSeconds()
    var dn = "AM"
    var dia = hoje.getDate()
    var dias = hoje.getDay()
    var mes = hoje.getMonth()
    var ano = hoje.getYear()

    /*if (hours>12){
        dn="PM"
        hours=hours-12
    }
    if (hours==0)
         hours=12*/

    if (minutes <= 9)
         minutes = "0" + minutes
    if (seconds <= 9)
        seconds = "0" + seconds
    if (dia < 10)
            dia = "0" + dia
    if (ano < 2000)
            ano = 1900 + ano
    if (ano == 100)
            ano= 1900 + ano

    NomeDia = new Array(7)
    NomeDia[0] = "Domingo"
    NomeDia[1] = "Segunda-feira"
    NomeDia[2] = "Ter?a-feira"
    NomeDia[3] = "Quarta-feira"
    NomeDia[4] = "Quinta-feira"
    NomeDia[5] = "Sexta-feira"
    NomeDia[6] = "S?bado"

    NomeMes = new Array(12)
    NomeMes[0] = "01"
    NomeMes[1] = "02"
    NomeMes[2] = "03"
    NomeMes[3] = "04"
    NomeMes[4] = "05"
    NomeMes[5] = "06"
    NomeMes[6] = "07"
    NomeMes[7] = "08"
    NomeMes[8] = "09"
    NomeMes[9] = "10"
    NomeMes[10] = "11"
    NomeMes[11] = "12"

    //change font size here to your desire
    myclock = '<span class="textobrancot" nowrap>' + NomeDia[dias] + ',&nbsp;' + dia + '/' + NomeMes[mes] + '/' + ano +'&nbsp;&nbsp;' + hours + ':' + minutes + ':' + seconds + '</span>';

    document.form1.usuarioOutputText.value = myclock;
//        if (document.layers){
//            document.layers.Layer1.document.open();
//            document.layers.Layer1.document.write(myclock);
//            document.layers.Layer1.document.close();
//        } else if (document.all) {
//            document.all["form1"].innerHTML = myclock;
//        } 
        
//        else if (document.getElementById) {
//            document.getElementById("form1").innerHTML = myclock;
//        }
        
    setTimeout("show5()",1000)
}
