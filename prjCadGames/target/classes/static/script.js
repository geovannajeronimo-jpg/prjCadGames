document.addEventListener("DOMContentLoaded",()=>{
   const cadastroForm = document.getElementById("cadastroForm");
   
   const pesquisaForm = document.getElementById("pesquisaForm");
   
   cadastroForm.addEventListener("submit",cadastrarJogo);
   
   pesquisaForm.addEventListener("submit",function(e){
       e.preventDefault();
       pesquisarJogo();
   });
});


function cadastrarJogo(event){
   event.preventDefault();
   
   const name = document.getElementById("name").value;
   const platform = document.getElementById("platform").value;
   const price = document.getElementById("price").value;
   const category = document.getElementById("category").value;
   const thumbnail=document.getElementById("thumbnail").files[0];
   
   const jogo = {
       name:name,
       platform:platform,
       price:price,
       category:category        
   }
   
   const formData = new FormData();
   
   formData.append(
       "jogo",
       new Blob([JSON.stringify(jogo)],{type:"application/json"})
   );
   
   formData.append("thumbnail",thumbnail)
   
   fetch("http://localhost:8080/jogos",{
       method:"POST",
       body:formData
   })
   .then(data=>{
       alert("Jogo Cadastrado com Sucesso");
   })
   .catch(error=>console.error(error))
   
}

function pesquisarJogo(){

const searchId = document.getElementById("searchId").value;

if(searchId === ""){
alert("Digite um ID");
return;
}

fetch(`http://localhost:8080/jogos/${searchId}`)
.then(response => {

if(response.status === 404){
throw new Error("Jogo não encontrado");
}

return response.json();

})
.then(data => {

document.getElementById("name").value = data.name;
document.getElementById("platform").value = data.platform;
document.getElementById("price").value = data.price;
document.getElementById("category").value = data.category;

const resultadoPesquisa = document.getElementById("resultadoPesquisa");

resultadoPesquisa.innerHTML = `
<h3>ID: ${data.id}</h3>
<img style="max-width:200px" src="data:image/jpeg;base64,${data.thumbnail}" alt="thumbnail do jogo">
`;

})
.catch(error => {

console.error(error);

const resultadoPesquisa = document.getElementById("resultadoPesquisa");

resultadoPesquisa.innerHTML = "Jogo não encontrado. Inserir ID válido";

});

}
function excluirJogo() {
   const searchId = document.getElementById("searchId").value;
   if (searchId === "") {
       alert("Digite um ID para exluir");
       return;
   }
   fetch(`http://localhost:8080/jogos/${searchId}`,{
       method:"DELETE"
   })
   .then(response =>{
       if(!response.ok){
           throw new Error("Erro ao exluir");
       }
       alert("Jogo Excluido com sucesso");
       document.getElementById("pesquisaForm").reset();
       document.getElementById("resultadoPesquisa").innerHTML="";
   }).catch(error => {
       console.error(error);
       alert("Erro ao excluir o Jogo");
   });    
}