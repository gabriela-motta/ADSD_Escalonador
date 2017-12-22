#coding: utf-8

txt = open('resultados_normal.txt', 'r')
csv = open('resultados_normal.csv', 'w')
csv.write('Distribuicao de chegada,Parametros,Valor medio servico,Duracao da simulacao,Quantidade de Requisicoes recebidas,Quantidade de Requisicoes atendidas,Tempo medio de atendimento,Quantidade media de elementos em espera')
csv.write('\n')
csvLine = ''

mBloco = 7
bloco = 0

for line in txt:
	if (bloco <= mBloco):
		bloco += 1
		parametro = line.split(': ')[1].rstrip('\n')
		csvLine += parametro + ','
	else:
		bloco = 0
		csv.write(csvLine[:-1])
		csv.write('\n')
		csvLine = ''
	
txt.close()
csv.close()
