HowTo: First Setup
------------------

1. Download SimpleJSON datasource from Grafana
- Download .zip-file at
	https://grafana.com/grafana/plugins/grafana-simple-json-datasource/installation
- Unzip it in /lib/plugins:
	/lib/plugins/grafana-simple-json-datasource/ should directly contain its files (e.g. package.json)

2. Start docker-compose
- Run "docker-compose up" in the /grafana/ folder

3. Login (admin/admin)
- http://localhost:3000/

4. Add datasource
- Configuration -> Datasources -> Add datasource -> Others: SimpleJson 

5. Set URL Configuration in SimpleJson Datasource:
URL -> http://aas-wrapper:6500/grafana/

6. Import Dashboard
- Dashboards -> Manage -> Import -> Upload dashboard.json

7. Open BaSyx Dashboard
- Dashboards -> Manage -> BaSyx Temperature
- Optional: Set auto-refresh to 1s

